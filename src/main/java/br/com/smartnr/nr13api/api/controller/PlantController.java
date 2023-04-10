package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.PlantAssembler;
import br.com.smartnr.nr13api.api.dto.request.PlantCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PlantDetailResponse;
import br.com.smartnr.nr13api.domain.service.PlantService;
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

}
