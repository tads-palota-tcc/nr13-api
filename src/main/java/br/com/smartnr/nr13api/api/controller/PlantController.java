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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
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
    @Secured({"INSTALLATION_WRITE"})
    public ResponseEntity<PlantDetailResponse> create(@RequestBody @Valid PlantCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Planta");
        var entity = plantService.create(plantAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/plants/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(plantAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    @Secured({"INSTALLATION_WRITE"})
    public ResponseEntity<PlantDetailResponse> update(@PathVariable Long id, @RequestBody @Valid PlantCreationRequest request) {
        log.info("Recebendo chamada para atualização de Planta");
        var entity = plantService.update(id, plantAssembler.toEntity(request));
        return ResponseEntity.ok(plantAssembler.toDetailResponse(entity));
    }

    @GetMapping
    @Secured({"INSTALLATION_READ"})
    public Page<PlantSummaryResponse> findByFilter(PlantFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Plantas");
        var entities = plantService.findByFilter(filter, pageable);
        return plantAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping("/from-user")
    @Secured({"INSTALLATION_READ"})
    public List<PlantSummaryResponse> findByUser() {
        log.info("Recebendo chamada para listagem de Plantas vinculadas ao usuário");
        var entities = plantService.findByUser();
        return plantAssembler.toSummaryList(entities);
    }

    @GetMapping("/top10")
    @Secured({"INSTALLATION_READ"})
    public List<PlantSummaryResponse> findTop10(@RequestParam(name = "code") String code) {
        log.info("Recebendo chamada para listagem das 10 primeiras Plantas ativas com código={}", code);
        var entities = plantService.findTop10(code);
        return plantAssembler.toSummaryList(entities);
    }

    @GetMapping("/{id}")
    @Secured({"INSTALLATION_READ"})
    public PlantDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Planta");
        var entity = plantService.findById(id);
        return plantAssembler.toDetailResponse(entity);
    }

    @GetMapping("/all")
    @Secured({"INSTALLATION_READ"})
    public List<PlantSummaryResponse> findAll() {
        log.info("Recebendo chamada para listagem de todas as Plantas");
        var entities = plantService.findAll();
        return plantAssembler.toSummaryList(entities);
    }


    @PatchMapping("/{id}/inactivate")
    @Secured({"INSTALLATION_WRITE"})
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        log.info("Recebendo chamada para inativação de Planta");
        plantService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @Secured({"INSTALLATION_WRITE"})
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        log.info("Recebendo chamada para ativação de Planta");
        plantService.activate(id);
        return ResponseEntity.noContent().build();
    }

}
