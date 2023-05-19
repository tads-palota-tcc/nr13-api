package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.repository.PendencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PendencyService {

    private final PendencyRepository pendencyRepository;
    private final UserService userService;

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
}
