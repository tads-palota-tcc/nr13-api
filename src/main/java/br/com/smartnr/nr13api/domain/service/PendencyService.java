package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.api.assembler.PendencyAssembler;
import br.com.smartnr.nr13api.api.dto.response.PendencyDetailResponse;
import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.repository.PendencyRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PendencyFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PendencySpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PendencyService {

    private final PendencyRepository pendencyRepository;
    private final UserService userService;
    private final PendencyAssembler assembler;

    @Transactional
    public Pendency create(Pendency entity) {
        log.info("Iniciando processo de cadastro de Pendência da inspeção Id={}", entity.getInspection().getId());
        var user = userService.getAuthenticatedUser();
        entity.setAuthor(user);
        entity.setOpenedAt(LocalDate.now());
        entity.setUpdatedBy(user);
        entity.setStatus(PendencyStatus.STARTED);
        return pendencyRepository.save(entity);
    }

    public Page<PendencyDetailResponse> findByFilter(PendencyFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Pendências por filtro={}", filter);
        var entities = pendencyRepository.findAll(PendencySpecs.withFilter(filter), pageable);
        return assembler.toPageResponse(entities);
    }

    public List<Pendency> findAllByInspectionId(Long inspectionId) {
        log.info("Iniciando processo de listagem de Pendências por Inspeção id={}", inspectionId);
        return pendencyRepository.findAllByInspectionIdOrderByDeadLineDesc(inspectionId);
    }
}
