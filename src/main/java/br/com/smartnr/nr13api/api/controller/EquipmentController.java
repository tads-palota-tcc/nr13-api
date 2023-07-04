package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.ApplicableTestAssembler;
import br.com.smartnr.nr13api.api.assembler.EquipmentAssembler;
import br.com.smartnr.nr13api.api.assembler.FileAssembler;
import br.com.smartnr.nr13api.api.dto.request.ApplicableTestRequest;
import br.com.smartnr.nr13api.api.dto.request.DocumentRequest;
import br.com.smartnr.nr13api.api.dto.request.EquipmentCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.EquipmentUpdateRequest;
import br.com.smartnr.nr13api.api.dto.response.EquipmentDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.EquipmentSituationResponse;
import br.com.smartnr.nr13api.api.dto.response.EquipmentSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.FileResponse;
import br.com.smartnr.nr13api.domain.model.DocumentType;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.repository.filters.EquipmentFilter;
import br.com.smartnr.nr13api.domain.service.EquipmentService;
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
import java.util.List;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
@Slf4j
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentAssembler equipmentAssembler;
    private final ApplicableTestAssembler applicableTestAssembler;
    private final FileStorageService fileStorageService;
    private final FileAssembler fileAssembler;

    @GetMapping
    @Secured({"EQUIPMENT_READ"})
    public Page<EquipmentSummaryResponse> findByFilter(EquipmentFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Equipamentos");
        var entities = equipmentService.findByFilter(filter, pageable);
        return equipmentAssembler.toSummaryPageResponse(entities);
    }

    @GetMapping(params = {"plantCode", "tag"})
    @Secured({"EQUIPMENT_READ"})
    public ResponseEntity<List<EquipmentSummaryResponse>> findByPlantAndTag(
            @RequestParam(name = "plantCode") String plantCode,
            @RequestParam(name = "tag") String tag) {
        log.info("Recebendo chamada para consulta de Equipamento por código da Planta e Tag");
        return ResponseEntity.ok(equipmentAssembler.toSummaryList(equipmentService.findTop10ByPlantCodeAndTag(plantCode, tag)));
    }

    @GetMapping("/{id}")
    @Secured({"EQUIPMENT_READ"})
    public EquipmentDetailResponse findById(@PathVariable Long id) {
        log.info("Recebendo chamada para consulta de Equipamento");
        var entity = equipmentService.findById(id);
        return equipmentAssembler.toDetailResponse(entity);
    }

    @PostMapping
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<EquipmentDetailResponse> create(@RequestBody @Valid EquipmentCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Equipamento");
        var entity = equipmentService.create(equipmentAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/equipments/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(equipmentAssembler.toDetailResponse(entity));
    }

    @PutMapping("/{id}")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<EquipmentDetailResponse> update(@PathVariable Long id, @RequestBody @Valid EquipmentUpdateRequest request) {
        log.info("Recebendo chamada para atualização de Equipamento");
        var entity = equipmentService.update(id, equipmentAssembler.toEntity(request));
        return ResponseEntity.ok(equipmentAssembler.toDetailResponse(entity));
    }

    @PatchMapping("/{id}/inactivate")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        log.info("Recebendo chamada para inativação de Equipamento");
        equipmentService.inactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        log.info("Recebendo chamada para ativação de Equipamento");
        equipmentService.activate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pressure-safety-valves/{psvId}")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> bindPsv(@PathVariable Long id, @PathVariable Long psvId) {
        log.info("Recebendo chamada para vincular Válvula de Segurança ao Equipamento");
        equipmentService.bindPsv(id, psvId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pressure-indicators/{piId}")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> bindPi(@PathVariable Long id, @PathVariable Long piId) {
        log.info("Recebendo chamada para vincular Indicador de Pressão ao Equipamento");
        equipmentService.bindPi(id, piId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/applicable-tests")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> addApplicableTest(@PathVariable Long id, @RequestBody @Valid ApplicableTestRequest request) {
        log.info("Recebendo chamada para adicionar teste aplicável ao Equipamento");
        var applicableTest = applicableTestAssembler.toEntity(request);
        equipmentService.addApplicableTest(id, applicableTest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/applicable-tests/{testId}")
    @Secured({"EQUIPMENT_WRITE"})
    ResponseEntity<Void> activateApplicableTest(@PathVariable Long id, @PathVariable Long testId) {
        log.info("Recebendo chamada para ativar teste aplicável");
        equipmentService.activateApplicableTest(id, testId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/pressure-safety-valves/{psvId}")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> unbindPsv(@PathVariable Long id, @PathVariable Long psvId) {
        log.info("Recebendo chamada para desvincular Válvula de Segurança do Equipamento");
        equipmentService.unbindPsv(id, psvId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/pressure-indicators/{piId}")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> unbindPi(@PathVariable Long id, @PathVariable Long piId) {
        log.info("Recebendo chamada para desvincular Indicador de Pressão do Equipamento");
        equipmentService.unbindPi(id, piId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/applicable-tests/{testId}")
    @Secured({"EQUIPMENT_WRITE"})
    ResponseEntity<Void> inactivateApplicableTest(@PathVariable Long id, @PathVariable Long testId) {
        log.info("Recebendo chamada para inativar teste aplicável");
        equipmentService.inactivateApplicableTest(id, testId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}/data-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<FileResponse> addDatabook(@PathVariable Long id, @Valid DocumentRequest request) throws IOException {
        log.info("Recebendo chamada para salvar prontuário em PDF");
        MultipartFile mpf = request.getFile();

        File file = new File();
        file.setName(mpf.getOriginalFilename());
        file.setType(mpf.getContentType());
        return ResponseEntity.ok(fileAssembler.toDetailResponse(equipmentService.addDocumentFile(id, mpf, DocumentType.DATA_BOOK)));
    }

    @PutMapping(path = "{id}/safety-journal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<FileResponse> addSafetyJournal(@PathVariable Long id, @Valid DocumentRequest request) throws IOException {
        log.info("Recebendo chamada para salvar registro de segurança em PDF");
        MultipartFile mpf = request.getFile();

        File file = new File();
        file.setName(mpf.getOriginalFilename());
        file.setType(mpf.getContentType());
        return ResponseEntity.ok(fileAssembler.toDetailResponse(equipmentService.addDocumentFile(id, mpf, DocumentType.SAFETY_JOURNAL)));
    }

    @PutMapping(path = "{id}/installation-project", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<FileResponse> addInstallationProject(@PathVariable Long id, @Valid DocumentRequest request) throws IOException {
        log.info("Recebendo chamada para salvar projeto de instalação em PDF");
        MultipartFile mpf = request.getFile();

        File file = new File();
        file.setName(mpf.getOriginalFilename());
        file.setType(mpf.getContentType());
        return ResponseEntity.ok(fileAssembler.toDetailResponse(equipmentService.addDocumentFile(id, mpf, DocumentType.INSTALLATION_PROJECT)));
    }

    @DeleteMapping("/{id}/data-book")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> deleteDatabook(@PathVariable Long id) throws IOException {
        log.info("Recebendo chamada para excluir prontuário");
        equipmentService.deleteDocument(id, DocumentType.DATA_BOOK);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/safety-journal")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> deleteSafetyJournal(@PathVariable Long id) throws IOException {
        log.info("Recebendo chamada para excluir registro de segurança");
        equipmentService.deleteDocument(id, DocumentType.SAFETY_JOURNAL);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/installation-project")
    @Secured({"EQUIPMENT_WRITE"})
    public ResponseEntity<Void> deleteInstallationProject(@PathVariable Long id) throws IOException {
        log.info("Recebendo chamada para excluir projeto de instalação");
        equipmentService.deleteDocument(id, DocumentType.INSTALLATION_PROJECT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/data-book", produces = MediaType.APPLICATION_PDF_VALUE)
    @Secured({"EQUIPMENT_READ"})
    public ResponseEntity<InputStreamResource> serveDatabook(@PathVariable Long id) {
        File report = equipmentService.getDocument(id, DocumentType.DATA_BOOK);
        InputStream inputStream = fileStorageService.retrieve(report.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping(path = "/{id}/safety-journal", produces = MediaType.APPLICATION_PDF_VALUE)
    @Secured({"EQUIPMENT_READ"})
    public ResponseEntity<InputStreamResource> serveSafetyJournal(@PathVariable Long id) {
        File report = equipmentService.getDocument(id, DocumentType.SAFETY_JOURNAL);
        InputStream inputStream = fileStorageService.retrieve(report.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping(path = "/{id}/installation-project", produces = MediaType.APPLICATION_PDF_VALUE)
    @Secured({"EQUIPMENT_READ"})
    public ResponseEntity<InputStreamResource> serveInstallationProject(@PathVariable Long id) {
        File report = equipmentService.getDocument(id, DocumentType.INSTALLATION_PROJECT);
        InputStream inputStream = fileStorageService.retrieve(report.getName());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/situation")
    @Secured({"EQUIPMENT_READ"})
    public ResponseEntity<Page<EquipmentSituationResponse>> findEquipmentsSituation(@RequestParam(name = "plantId", required = false) Long plantId, Pageable pageable) {
        var entities = equipmentService.findAllByPlantId(plantId, pageable);
        return ResponseEntity.ok(equipmentAssembler.toSituationPageResponse(entities));
    }

}
