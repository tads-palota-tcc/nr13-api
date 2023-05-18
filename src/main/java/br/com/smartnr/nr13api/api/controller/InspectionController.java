package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.InspectionAssembler;
import br.com.smartnr.nr13api.api.dto.request.InspectionCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.InspectionSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.service.InspectionService;
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

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
@Slf4j
public class InspectionController {

    private final InspectionService inspectionService;
    private final InspectionAssembler inspectionAssembler;

    @PostMapping
    public ResponseEntity<InspectionSummaryResponse> create(@RequestBody @Valid InspectionCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Inspeção");
        var entity = inspectionService.create(inspectionAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/inspections/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(inspectionAssembler.toSummaryResponse(entity));
    }

    @GetMapping
    public ResponseEntity<Page<InspectionSummaryResponse>> findByFilter(InspectionFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Inspeções");
        var entities = inspectionService.findByFilter(filter, pageable);
        return ResponseEntity.ok(inspectionAssembler.toSummaryPageResponse(entities));
    }

    @GetMapping("/{id}")
    public InspectionSummaryResponse findById(@PathVariable Long id) {
        return inspectionAssembler.toSummaryResponse(inspectionService.findById(id));
    }
}
