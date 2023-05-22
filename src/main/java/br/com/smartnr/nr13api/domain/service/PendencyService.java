package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.PendencyNotFoundException;
import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.repository.PendencyRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PendencyFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PendencySpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

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

    public Page<Pendency> findByFilter(PendencyFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Pendências por filtro={}", filter);
        return pendencyRepository.findAll(PendencySpecs.withFilter(filter), pageable);
    }

    public List<Pendency> findAllByInspectionId(Long inspectionId) {
        log.info("Iniciando processo de listagem de Pendências por Inspeção id={}", inspectionId);
        return pendencyRepository.findAllByInspectionIdOrderByDeadLineDesc(inspectionId);
    }

    @Transactional
    public Pendency update(Long id, Pendency entity) {
        log.info("Iniciando processo de atualização de Pendência Id={}", id);
        var existing = this.findOrFail(id);
        modelMapper.map(entity, existing);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        pendencyRepository.save(existing);
        return existing;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Iniciando processo de exclurão de Pendência Id={}", id);
        var existing = this.findOrFail(id);
        pendencyRepository.delete(existing);
    }

    public Pendency findById(Long id) {
        log.info("Iniciando busca de Pendência id={}", id);
        return findOrFail(id);
    }

    private Pendency findOrFail(Long id) {
        return pendencyRepository.findById(id)
                .orElseThrow(() -> new PendencyNotFoundException(id));
    }
}
