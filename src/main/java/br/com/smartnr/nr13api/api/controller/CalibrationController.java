package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.dto.request.CalibrationCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.CalibrationDetailResponse;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
}
