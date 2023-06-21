package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.dto.response.TestResponse;
import br.com.smartnr.nr13api.domain.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    @Secured({"EQUIPMENT_WRITE", "EQUIPMENT_READ"})
    public List<TestResponse> findAll() {
        log.info("Recebendo chamada para listagem de Testes");
        var entities = testRepository.findAll();
        return entities.stream().map(e -> modelMapper.map(e, TestResponse.class)).collect(Collectors.toList());
    }

}
