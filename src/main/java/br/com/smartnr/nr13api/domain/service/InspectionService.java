package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.FileNotFoundException;
import br.com.smartnr.nr13api.domain.exception.InspectionNotFoundException;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.model.Inspection;
import br.com.smartnr.nr13api.domain.model.Status;
import br.com.smartnr.nr13api.domain.repository.ApplicableTestRepository;
import br.com.smartnr.nr13api.domain.repository.FileRepository;
import br.com.smartnr.nr13api.domain.repository.InspectionRepository;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.repository.specs.InspectionSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final TestService testService;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final ApplicableTestRepository applicableTestRepository;
    private final EquipmentService equipmentService;
    private final ModelMapper modelMapper;

    @Transactional
    public Inspection create(Inspection entity) {
        log.info("Iniciando processo de cadastro de Inspeção: Equipamento={}, Teste={}",
                entity.getApplicableTest().getId().getEquipment().getId(),
                entity.getApplicableTest().getId().getTest().getId());
        try {
            var equipment = equipmentService.findById(entity.getApplicableTest().getId().getEquipment().getId());
            var test = testService.findById(entity.getApplicableTest().getId().getTest().getId());
            if (!equipment.getApplicableTests().contains(entity.getApplicableTest())) {
                throw new BusinessException(String.format("%s não é um teste aplicável ao Equipamento Id=%d", test.getName(), equipment.getId()));
            }
            entity.getApplicableTest().getId().setEquipment(equipment);

            for (ApplicableTest at : equipment.getApplicableTests()) {
                if (at.equals(entity.getApplicableTest())) {
                    at.setLastTestDate(entity.getExecutionDate());
                    applicableTestRepository.save(at);
                }
            }
            entity.setUpdatedBy(userService.getAuthenticatedUser());
            entity = inspectionRepository.save(entity);
            log.info("Cadastro de Inspeção realizado com sucesso id={}", entity.getId());
            return entity;
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @Transactional
    public Inspection update(Long id, Inspection entity) {
        log.info("Iniciando processo de atualização de Inspeção Id={}", entity.getId());
        if (entity.getStatus().equals(Status.DONE) && ObjectUtils.isEmpty(entity.getReportNumber())) {
            throw new BusinessException("Para status Concluído, o número do relatório deve ser informado");
        }

        if (!ObjectUtils.isEmpty(entity.getReportNumber()) && !entity.getStatus().equals(Status.DONE)) {
            throw new BusinessException("Para calibração com relatório informado, o status deve ser Concluído");
        }
        var existing = this.findOrFail(id);
        var test = testService.findById(entity.getApplicableTest().getId().getTest().getId());
        var equipment = equipmentService.findById(entity.getApplicableTest().getId().getEquipment().getId());
        entity.getApplicableTest().getId().setTest(test);
        entity.getApplicableTest().getId().setEquipment(equipment);
        if (!equipment.getApplicableTests().contains(entity.getApplicableTest())) {
            throw new BusinessException(String.format("%s não é um teste aplicável ao Equipamento Id=%d", test.getName(), equipment.getId()));
        }
        modelMapper.map(entity, existing);
        var user = userService.getAuthenticatedUser();
        existing.setUpdatedBy(user);
        existing.getApplicableTest().setLastTestDate(entity.getExecutionDate());
        existing.getApplicableTest().setUpdatedBy(user);
        applicableTestRepository.save(existing.getApplicableTest());
        inspectionRepository.save(existing);
        return existing;
    }

    public Page<Inspection> findByFilter(InspectionFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Calibração filtro={}", filter);
        var page = inspectionRepository.findAll(InspectionSpecs.withFilter(filter, null), pageable);
        return page;
    }

    public Inspection findById(Long id) {
        return findOrFail(id);
    }

    private Inspection findOrFail(Long id) {
        return inspectionRepository.findById(id)
                .orElseThrow(() -> new InspectionNotFoundException(id));
    }

    @Transactional
    public File addReportFile(Long inspectionId, MultipartFile multipartFile) throws IOException {
        var inspection = findOrFail(inspectionId);
        String oldFileName = null;
        if (!ObjectUtils.isEmpty(inspection.getFile())) {
            oldFileName = inspection.getFile().getName();
        }
        String newFileName = fileStorageService.generateFileName(multipartFile.getOriginalFilename());
        var file = new File();
        file.setName(newFileName);
        file.setType(multipartFile.getContentType());
        file.setUpdatedBy(userService.getAuthenticatedUser());
        file.setUrl("/home/apagar/tcc/123.pdf");
        file = fileRepository.save(file);
        inspection.setFile(file);
        inspectionRepository.save(inspection);
        var newFile = FileStorageService.NewFile.builder()
                .fileName(file.getName())
                .inputStream(multipartFile.getInputStream())
                .build();
        fileStorageService.replace(oldFileName, newFile);
        return file;
    }

    public File getReportByCalibrationId(Long id) {
        log.info("Iniciando busca de relatório de inspeção id={}", id);
        var entity = findOrFail(id);
        if (ObjectUtils.isEmpty(entity.getFile())) {
            throw new FileNotFoundException(id);
        }
        return entity.getFile();
    }
}
