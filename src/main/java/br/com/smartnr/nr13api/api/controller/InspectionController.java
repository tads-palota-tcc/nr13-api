package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.FileAssembler;
import br.com.smartnr.nr13api.api.assembler.InspectionAssembler;
import br.com.smartnr.nr13api.api.dto.request.InspectionCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.InspectionReportRequest;
import br.com.smartnr.nr13api.api.dto.response.FileResponse;
import br.com.smartnr.nr13api.api.dto.response.InspectionSummaryResponse;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.service.FileStorageService;
import br.com.smartnr.nr13api.domain.service.InspectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
@Slf4j
public class InspectionController {

    private final InspectionService inspectionService;
    private final InspectionAssembler inspectionAssembler;
    private final FileStorageService fileStorageService;
    private final FileAssembler fileAssembler;

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
        log.info("Recebendo chamada para consulta de Inspeção por Id={}", id);
        return inspectionAssembler.toSummaryResponse(inspectionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectionSummaryResponse> update(@PathVariable Long id, @RequestBody @Valid InspectionCreationRequest request) {
        log.info("Recebendo chamada para atualizar uma Inspeção");
        var entity = inspectionService.update(id, inspectionAssembler.toEntity(request));
        return ResponseEntity.ok(inspectionAssembler.toSummaryResponse(entity));
    }

    @PutMapping(path = "{id}/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> addReport(@PathVariable Long id, @Valid InspectionReportRequest request) throws IOException {
        log.info("Recebendo chamada para salvar relatório em PDF");
        MultipartFile mpf = request.getFile();

        File file = new File();
        file.setName(mpf.getOriginalFilename());
        file.setType(mpf.getContentType());
        return ResponseEntity.ok(fileAssembler.toDetailResponse(inspectionService.addReportFile(id, mpf)));
    }

    @GetMapping(path = "/{id}/reports", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> serveReport(@PathVariable Long id) {
        File report = inspectionService.getReportByCalibrationId(id);
        InputStream inputStream = fileStorageService.retrieve(report.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
}
