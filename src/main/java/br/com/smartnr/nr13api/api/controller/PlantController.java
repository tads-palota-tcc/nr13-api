package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.PlantAssembler;
import br.com.smartnr.nr13api.api.dto.request.PlantCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PlantDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.PlantSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.PlantFilter;
import br.com.smartnr.nr13api.domain.service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/plants")
@RequiredArgsConstructor
@Slf4j
public class PlantController {

    private final PlantService plantService;
    private final PlantAssembler plantAssembler;

    @PostMapping
    public ResponseEntity<PlantDetailResponse> create(@RequestBody @Valid PlantCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Planta");
        var entity = plantService.create(plantAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/plants/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(plantAssembler.toDetailResponse(entity));
    }

    @GetMapping
    public Page<PlantSummaryResponse> findByFilter(PlantFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Plantas");
        var entities = plantService.findByFilter(filter, pageable);
        return plantAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping("/from-user")
    public List<PlantSummaryResponse> findByUser() {
        log.info("Recebendo chamada para listagem de Plantas vinculadas ao usuário");
        var entities = plantService.findByUser();
        return plantAssembler.toSummaryList(entities);
    }

    @GetMapping("/{id}")
    public PlantDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Planta");
        var entity = plantService.findById(id);
        return plantAssembler.toDetailResponse(entity);
    }


}
