package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.ApplicableTestAssembler;
import br.com.smartnr.nr13api.api.assembler.EquipmentAssembler;
import br.com.smartnr.nr13api.api.dto.request.ApplicableTestRequest;
import br.com.smartnr.nr13api.api.dto.request.EquipmentCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.EquipmentUpdateRequest;
import br.com.smartnr.nr13api.api.dto.response.EquipmentDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.EquipmentSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.filters.EquipmentFilter;
import br.com.smartnr.nr13api.domain.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
@Slf4j
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentAssembler equipmentAssembler;
    private final ApplicableTestAssembler applicableTestAssembler;

    @GetMapping
    public Page<EquipmentSummaryResponse> findByFilter(EquipmentFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Equipamentos");
        var entities = equipmentService.findByFilter(filter, pageable);
        return equipmentAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping(params = {"plantCode", "tag"})
    public ResponseEntity<List<EquipmentSummaryResponse>> findByPlantAndTag(
            @RequestParam(name = "plantCode") String plantCode,
            @RequestParam(name = "tag") String tag) {
        log.info("Recebendo chamada para consulta de Equipamento por código da Planta e Tag");
        return ResponseEntity.ok(equipmentAssembler.toSummaryList(equipmentService.findTop10ByPlantCodeAndTag(plantCode, tag)));
    }

    @GetMapping("/{id}")
    public EquipmentDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Equipamento");
        var entity = equipmentService.findById(id);
        return equipmentAssembler.toDetailResponse(entity);
    }

    @PostMapping
    public ResponseEntity<EquipmentDetailResponse> create(@RequestBody EquipmentCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Equipamento");
        var entity = equipmentService.create(equipmentAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/equipments/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(equipmentAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDetailResponse> update(@PathVariable Long id, @RequestBody @Valid EquipmentUpdateRequest request) {
        log.info("Recebendo chamada para atualização de Equipamento");
        var entity = equipmentService.update(id, equipmentAssembler.toEntity(request));
        return ResponseEntity.ok(equipmentAssembler.toDetailResponse(entity));
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        log.info("Recebendo chamada para inativação de Equipamento");
        equipmentService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        log.info("Recebendo chamada para ativação de Equipamento");
        equipmentService.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pressure-safety-valves/{psvId}")
    public ResponseEntity<Void> bindPsv(@PathVariable Long id, @PathVariable Long psvId) {
        log.info("Recebendo chamada para vincular Válvula de Segurança ao Equipamento");
        equipmentService.bindPsv(id, psvId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pressure-indicators/{piId}")
    public ResponseEntity<Void> bindPi(@PathVariable Long id, @PathVariable Long piId) {
        log.info("Recebendo chamada para vincular Indicador de Pressão ao Equipamento");
        equipmentService.bindPi(id, piId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/applicable-tests")
    public ResponseEntity<Void> addApplicableTest(@PathVariable Long id, @RequestBody @Valid ApplicableTestRequest request) {
        log.info("Recebendo chamada para adicionar teste aplicável ao Equipamento");
        var applicableTest = applicableTestAssembler.toEntity(request);
        equipmentService.addApplicableTest(id, applicableTest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/applicable-tests/{testId}")
    ResponseEntity<Void> activateApplicableTest(@PathVariable Long id, @PathVariable Long testId) {
        log.info("Recebendo chamada para ativar teste aplicável");
        equipmentService.activateApplicableTest(id, testId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/pressure-safety-valves/{psvId}")
    public ResponseEntity<Void> unbindPsv(@PathVariable Long id, @PathVariable Long psvId) {
        log.info("Recebendo chamada para desvincular Válvula de Segurança do Equipamento");
        equipmentService.unbindPsv(id, psvId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/pressure-indicators/{piId}")
    public ResponseEntity<Void> unbindPi(@PathVariable Long id, @PathVariable Long piId) {
        log.info("Recebendo chamada para desvincular Indicador de Pressão do Equipamento");
        equipmentService.unbindPi(id, piId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/applicable-tests/{testId}")
    ResponseEntity<Void> inactivateApplicableTest(@PathVariable Long id, @PathVariable Long testId) {
        log.info("Recebendo chamada para inativar teste aplicável");
        equipmentService.inactivateApplicableTest(id, testId);
        return ResponseEntity.noContent().build();
    }

}
