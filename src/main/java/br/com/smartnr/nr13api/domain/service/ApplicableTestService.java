package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.ApplicableTest;
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
}
