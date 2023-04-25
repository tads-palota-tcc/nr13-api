package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.PressureIndicatorAssembler;
import br.com.smartnr.nr13api.api.dto.request.PressureIndicatorCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PressureIndicatorDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.PressureIndicatorSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.PressureIndicatorFilter;
import br.com.smartnr.nr13api.domain.service.PressureIndicatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pressure-indicators")
@RequiredArgsConstructor
@Slf4j
public class PressureIndicatorController {

    private final PressureIndicatorService piService;
    private final PressureIndicatorAssembler piAssembler;

    @GetMapping
    public Page<PressureIndicatorSummaryResponse> findByFilter(PressureIndicatorFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Indicador de Pressão");
        var entities = piService.findByFilter(filter, pageable);
        return piAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping("/available")
    public List<PressureIndicatorSummaryResponse> findAllAvailable() {
        log.info("Recebendo chamada para listagem de Indicadores de Pressão disponíveis");
        var entity = piService.findAllAvailableByUserPlant();
        return piAssembler.toSummaryList(entity);
    }

    @GetMapping("/{id}")
    public PressureIndicatorDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Indicador de Pressão por Id={}", id);
        var entity = piService.findById(id);
        return piAssembler.toDetailResponse(entity);
    }

    @PostMapping
    public ResponseEntity<PressureIndicatorDetailResponse> create(@RequestBody @Valid PressureIndicatorCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Indicador de Pressão");
        var entity = piService.create(piAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/pressure-indicators/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(piAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PressureIndicatorDetailResponse> update(@PathVariable Long id, @RequestBody @Valid PressureIndicatorCreationRequest request) {
        log.info("Recebendo chamada para atualização de Indicador de Pressão");
        var entity = piService.update(id, piAssembler.toEntity(request));
        return ResponseEntity.ok(piAssembler.toDetailResponse(entity));
    }

}