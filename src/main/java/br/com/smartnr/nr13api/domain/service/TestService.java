package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.TestNotFoundException;
import br.com.smartnr.nr13api.domain.model.Test;
import br.com.smartnr.nr13api.domain.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;

    public Test findById(Long id) {
        log.info("Iniciando busca de Teste id={}", id);
        return findOrFail(id);
    }

    private Test findOrFail(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException(id));
    }
}
