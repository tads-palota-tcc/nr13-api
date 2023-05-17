package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.InspectionAssembler;
import br.com.smartnr.nr13api.api.dto.response.InspectionSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.service.InspectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
@Slf4j
public class InspectionController {

    private final InspectionService inspectionService;
    private final InspectionAssembler inspectionAssembler;

    @GetMapping
    public ResponseEntity<Page<InspectionSummaryResponse>> findByFilter(InspectionFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Inspeções");
        var entities = inspectionService.findByFilter(filter, pageable);
        return ResponseEntity.ok(inspectionAssembler.toSummaryPageResponse(entities));
    }
}
