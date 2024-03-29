package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.assembler.FileAssembler;
import br.com.smartnr.nr13api.api.dto.request.CalibrationCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.CalibrationReportRequest;
import br.com.smartnr.nr13api.api.dto.response.CalibrationDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.FileResponse;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.repository.filters.CalibrationFilter;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import br.com.smartnr.nr13api.domain.service.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping("/calibrations")
@RequiredArgsConstructor
@Slf4j
public class CalibrationController {

    private final CalibrationService calibrationService;
    private final CalibrationAssembler calibrationAssembler;
    private final FileAssembler fileAssembler;
    private final FileStorageService fileStorageService;

    @PostMapping
    @Secured({"INTERVENTION_WRITE"})
    public ResponseEntity<CalibrationDetailResponse> create(@RequestBody @Valid CalibrationCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Calibração");
        var entity = calibrationService.create(calibrationAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/calibrations/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(calibrationAssembler.toDetailResponse(entity));
    }

    @GetMapping
    @Secured({"INTERVENTION_READ"})
    public ResponseEntity<Page<CalibrationSummaryResponse>> findByFilter(CalibrationFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Calibrações");
        var entities = calibrationService.findByFilter(filter, pageable);
        return ResponseEntity.ok(calibrationAssembler.toSummaryPageResponse(entities));
    }

    @GetMapping("/{id}")
    @Secured({"INTERVENTION_READ"})
    public ResponseEntity<CalibrationDetailResponse> findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Calibração");
        var entity = calibrationService.findById(id);
        return ResponseEntity.ok(calibrationAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    @Secured({"INTERVENTION_WRITE"})
    public ResponseEntity<CalibrationDetailResponse> update(@PathVariable Long id, @RequestBody @Valid CalibrationCreationRequest request) {
        log.info("Recebendo chamada para atualizar uma Calibração");
        var entity = calibrationService.update(id, calibrationAssembler.toEntity(request));
        return ResponseEntity.ok(calibrationAssembler.toDetailResponse(entity));
    }

    @DeleteMapping("{id}")
    @Secured({"INTERVENTION_WRITE"})
    private ResponseEntity<Void> delete(@PathVariable Long id) throws IOException {
        log.info("Recebendo chamada para excluir calibração");
        calibrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"INTERVENTION_WRITE"})
    public ResponseEntity<FileResponse> addReport(@PathVariable Long id, @Valid CalibrationReportRequest request) throws IOException {
        log.info("Recebendo chamada para salvar relatório em PDF");
        MultipartFile mpf = request.getFile();

        File file = new File();
        file.setName(mpf.getOriginalFilename());
        file.setType(mpf.getContentType());
        return ResponseEntity.ok(fileAssembler.toDetailResponse(calibrationService.addReportFile(id, mpf)));
    }

    @DeleteMapping("/{id}/reports")
    @Secured({"INTERVENTION_WRITE"})
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) throws IOException {
        log.info("Recebendo chamada para excluir relatório");
        calibrationService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/reports", produces = MediaType.APPLICATION_PDF_VALUE)
    @Secured({"INTERVENTION_READ"})
    public ResponseEntity<InputStreamResource> serveReport(@PathVariable Long id) {
        File report = calibrationService.getReportByCalibrationId(id);
        InputStream inputStream = fileStorageService.retrieve(report.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
}
