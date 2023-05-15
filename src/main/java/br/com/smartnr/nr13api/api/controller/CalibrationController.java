package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.dto.request.CalibrationCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.CalibrationReportRequest;
import br.com.smartnr.nr13api.api.dto.response.CalibrationDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.CalibrationFilter;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/calibrations")
@RequiredArgsConstructor
@Slf4j
public class CalibrationController {

    private final CalibrationService calibrationService;
    private final CalibrationAssembler calibrationAssembler;

    @PostMapping
    public ResponseEntity<CalibrationDetailResponse> create(@RequestBody @Valid CalibrationCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Calibração");
        var entity = calibrationService.create(calibrationAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/calibrations/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(calibrationAssembler.toDetailResponse(entity));
    }

    @GetMapping
    public ResponseEntity<Page<CalibrationSummaryResponse>> findByFilter(CalibrationFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Calibrações");
        var entities = calibrationService.findByFilter(filter, pageable);
        return ResponseEntity.ok(calibrationAssembler.toSummaryPageResponse(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalibrationDetailResponse> findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Calibração");
        var entity = calibrationService.findById(id);
        return ResponseEntity.ok(calibrationAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalibrationDetailResponse> update(@PathVariable Long id, @RequestBody @Valid CalibrationCreationRequest request) {
        log.info("Recebendo chamada para atualizar uma Calibração");
        var entity = calibrationService.update(id, calibrationAssembler.toEntity(request));
        return ResponseEntity.ok(calibrationAssembler.toDetailResponse(entity));
    }

    @PutMapping(path = "{id}/reports", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addReport(@PathVariable Long id, @Valid CalibrationReportRequest request) {

        var fileName = UUID.randomUUID() + "_" + request.getFile().getOriginalFilename();

        var reportFile = Path.of("/home/alexandre/apagar/tcc", fileName);

        System.out.println(reportFile);
        System.out.println(request.getFile().getContentType());

        try {
            request.getFile().transferTo(reportFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
