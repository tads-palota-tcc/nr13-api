package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.ApplicableTestNotFoundException;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.ApplicableTestPK;
import br.com.smartnr.nr13api.domain.repository.ApplicableTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicableTestService {

    private final ApplicableTestRepository applicableTestRepository;

    @Transactional
    public ApplicableTest create(ApplicableTest applicableTest) {
        log.info("Iniciando processo de cadastro de Teste Aplicável: Id do equipamento={}, Id do teste={}",
                applicableTest.getId().getEquipment().getId(), applicableTest.getId().getTest().getId());
        return applicableTestRepository.save(applicableTest);
    }

    @Transactional
    public void inactivate(ApplicableTestPK id) {
        var entity = findOrFail(id);
        entity.setActive(Boolean.FALSE);
    }

    @Transactional
    public void activate(ApplicableTestPK id) {
        var entity = findOrFail(id);
        entity.setActive(Boolean.TRUE);
    }

    private ApplicableTest findOrFail(ApplicableTestPK id) {
        return applicableTestRepository.findById(id)
                .orElseThrow(() -> new ApplicableTestNotFoundException(id.getTest().getId(), id.getEquipment().getId()));
    }
}
