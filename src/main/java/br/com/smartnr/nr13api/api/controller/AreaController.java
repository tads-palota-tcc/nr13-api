package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.AreaAssembler;
import br.com.smartnr.nr13api.api.dto.request.AreaCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.AreaDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.AreaSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.AreaFilter;
import br.com.smartnr.nr13api.domain.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
@Slf4j
public class AreaController {

    private final AreaService areaService;
    private final AreaAssembler areaAssembler;

    @PostMapping
    public ResponseEntity<AreaDetailResponse> create(@RequestBody @Valid AreaCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Área");
        var entity = areaService.create(areaAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/areas/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(areaAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDetailResponse> update(@PathVariable Long id, @RequestBody @Valid AreaCreationRequest request) {
        log.info("Recebendo chamada para atualização de Área");
        var entity = areaService.update(id, areaAssembler.toEntity(request));
        return ResponseEntity.ok(areaAssembler.toDetailResponse(entity));
    }

    @GetMapping
    public Page<AreaSummaryResponse> findByFilter(AreaFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Áreas");
        var entities = areaService.findByFilter(filter, pageable);
        return areaAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping("/{id}")
    public AreaDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Área");
        var entity = areaService.findById(id);
        return areaAssembler.toDetailResponse(entity);
    }

    @GetMapping("/top10")
    public List<AreaSummaryResponse> findTop10(@RequestParam(name = "code") String code) {
        log.info("Recebendo chamada para listagem das 10 primeiras Áreas ativas com código={}", code);
        var entities = areaService.findTop10(code);
        return areaAssembler.toSummaryList(entities);
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        log.info("Recebendo chamada para inativação de Área");
        areaService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        log.info("Recebendo chamada para ativação de Área");
        areaService.activate(id);
        return ResponseEntity.noContent().build();
    }

}
