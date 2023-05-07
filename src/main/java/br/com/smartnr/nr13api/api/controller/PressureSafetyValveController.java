package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.PressureSafetyValveAssembler;
import br.com.smartnr.nr13api.api.dto.request.PressureSafetyValveCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PressureSafetyValveDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.PressureSafetyValveSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.PressureSafetyValveFilter;
import br.com.smartnr.nr13api.domain.service.PressureSafetyValveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pressure-safety-valves")
@RequiredArgsConstructor
@Slf4j
public class PressureSafetyValveController {

    private final PressureSafetyValveService psvService;
    private final PressureSafetyValveAssembler psvAssembler;

    @GetMapping
    public Page<PressureSafetyValveSummaryResponse> findByFilter(PressureSafetyValveFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Válvula de Segurança");
        var entities = psvService.findByFilter(filter, pageable);
        return psvAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping("/available")
    public List<PressureSafetyValveSummaryResponse> findAllAvailable() {
        log.info("Recebendo chamada para listagem de Válvulas de Segurança disponíveis");
        var entity = psvService.findAllAvailableByUserPlant();
        return psvAssembler.toSummaryList(entity);
    }

    @GetMapping("/{id}")
    public PressureSafetyValveDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Válvula de Segurança por Id={}", id);
        var entity = psvService.findById(id);
        return psvAssembler.toDetailResponse(entity);
    }

    @PostMapping
    public ResponseEntity<PressureSafetyValveDetailResponse> create(@RequestBody @Valid PressureSafetyValveCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Válvula de Segurança");
        var entity = psvService.create(psvAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/pressure-indicators/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(psvAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PressureSafetyValveDetailResponse> update(@PathVariable Long id, @RequestBody @Valid PressureSafetyValveCreationRequest request) {
        log.info("Recebendo chamada para atualização de Válvula de Segurança");
        var entity = psvService.update(id, psvAssembler.toEntity(request));
        return ResponseEntity.ok(psvAssembler.toDetailResponse(entity));
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        log.info("Recebendo chamada para inativação de Válvula de Segurança");
        psvService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        log.info("Recebendo chamada para ativação de Válvula de Segurança");
        psvService.activate(id);
        return ResponseEntity.noContent().build();
    }

}
