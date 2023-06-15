package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.EquipmentNotFoundException;
import br.com.smartnr.nr13api.domain.exception.FileNotFoundException;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.DocumentType;
import br.com.smartnr.nr13api.domain.model.Equipment;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.repository.EquipmentRepository;
import br.com.smartnr.nr13api.domain.repository.FileRepository;
import br.com.smartnr.nr13api.domain.repository.filters.EquipmentFilter;
import br.com.smartnr.nr13api.domain.repository.specs.EquipmentSpecs;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final AreaService areaService;
    private final UserService userService;
    private final TestService testService;
    private final PressureSafetyValveService psvService;
    private final PressureIndicatorService piService;
    private final ApplicableTestService applicableTestService;
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Equipment create(Equipment equipment) {
        log.info("Iniciando processo de cadastro de Equipamento: {}", equipment.getTag());
        try {
            var area = areaService.findById(equipment.getArea().getId());
            equipment.setUpdatedBy(userService.getAuthenticatedUser());
            equipment.setArea(area);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
        equipment = equipmentRepository.save(equipment);
        log.info("Cadastro de Equipamento realizado com sucesso id={}", equipment.getId());
        return equipment;
    }

    @Transactional
    public Equipment update(Long id, Equipment equipment) {
        log.info("Iniciando processo de atualização de Equipamento: {}", equipment.getTag());
        var existing = this.findOrFail(id);
        modelMapper.map(equipment, existing);
        var area = areaService.findById(equipment.getArea().getId());
        equipment.setArea(area);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        equipmentRepository.save(existing);
        return existing;
    }

    @Transactional
    public void inactivate(Long id) {
        log.info("Iniciando processo de inativação de Equipamento Id={}", id);
        var entity = this.findOrFail(id);
        if (!entity.getActive()) {
            throw new BusinessException(String.format("Equipamento com id=%d já encontra-se inativo", id));
        }
        entity.setActive(Boolean.FALSE);
    }

    @Transactional
    public void activate(Long id) {
        log.info("Iniciando processo de ativação de Equipamento Id={}", id);
        var entity = this.findOrFail(id);
        if (entity.getActive()) {
            throw new BusinessException(String.format("Equipamento com id=%d já encontra-se ativo", id));
        }
        entity.setActive(Boolean.TRUE);
    }

    public Page<Equipment> findByFilter(EquipmentFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Equipamento filtro={}", filter);
        return equipmentRepository.findAll(EquipmentSpecs.withFilter(filter), pageable);
    }

    public Equipment findById(Long id) {
        log.info("Iniciando busca de Equipamento id={}", id);
        return findOrFail(id);
    }

    public List<Equipment> findAll() {
        log.info("Iniciando processo de listagem de todos os Equipamentos");
        return equipmentRepository.findAll();
    }

    public List<Equipment> findAllByPlantId(Long plantId) {
        log.info("Iniciando processo de listagem de Equipamento por planta Id={}", plantId);
        return equipmentRepository.findAllByAreaPlantId(plantId);
    }

    public List<Equipment> findTop10ByPlantCodeAndTag(String plantCode, String tag) {
        log.info("Iniciando consulta de Equipamentos por Planta={} e Tag={}", plantCode, tag);
        return equipmentRepository.findTop10ByAreaPlantCodeAndTagContainingIgnoreCaseOrderByTag(plantCode, tag);
    }

    @Transactional
    public void bindPsv(Long id, Long psvId) {
        var equipment = findOrFail(id);
        var psv = psvService.findById(psvId);
        equipment.addPressureSafetyValve(psv);
    }

    @Transactional
    public void unbindPsv(Long id, Long psvId) {
        var equipment = findOrFail(id);
        var psv = psvService.findById(psvId);
        equipment.removePressureSafetyValve(psv);
    }

    @Transactional
    public void bindPi(Long id, Long piId) {
        var equipment = findOrFail(id);
        var pi = piService.findById(piId);
        equipment.addPressureIndicator(pi);
    }

    @Transactional
    public void unbindPi(Long id, Long piId) {
        var equipment = findOrFail(id);
        var pi = piService.findById(piId);
        equipment.removePressureIndicator(pi);
    }

    @Transactional
    public void addApplicableTest(Long id, ApplicableTest applicableTest) {
        var equipment = findOrFail(id);
        var test = testService.findById(applicableTest.getId().getTest().getId());
        applicableTest.getId().setEquipment(equipment);
        applicableTest.getId().setTest(test);
        applicableTest.setUpdatedBy(userService.getAuthenticatedUser());
        applicableTestService.create(applicableTest);
    }

    public void activateApplicableTest(Long id, Long testId) {
        var equipment = findOrFail(id);
        equipment.getApplicableTests().forEach(at -> {
            if (at.getId().getTest().getId().equals(testId)) {
                applicableTestService.activate(at.getId());
            }
        });
    }

    public void inactivateApplicableTest(Long id, Long testId) {
        var equipment = findOrFail(id);
        equipment.getApplicableTests().forEach(at -> {
            if (at.getId().getTest().getId().equals(testId)) {
                applicableTestService.inactivate(at.getId());
            }
        });
    }

    @Transactional
    public File addDocumentFile(Long equipmentId, MultipartFile multipartFile, DocumentType type) throws IOException {
        var equipment = findOrFail(equipmentId);
        String oldFileName = null;
        switch (type) {
            case DATA_BOOK -> {
                if (!ObjectUtils.isEmpty(equipment.getDatabookFile())) {
                    oldFileName = equipment.getDatabookFile().getName();
                }
            }
            case SAFETY_JOURNAL -> {
                if (!ObjectUtils.isEmpty(equipment.getSafetyJournalFile())) {
                    oldFileName = equipment.getSafetyJournalFile().getName();
                }
            }
            case INSTALLATION_PROJECT -> {
                if (!ObjectUtils.isEmpty(equipment.getInstallationProjectFile())) {
                    oldFileName = equipment.getInstallationProjectFile().getName();
                }
            }
        }
        String newFileName = fileStorageService.generateFileName(multipartFile.getOriginalFilename());
        var file = new File();
        file.setName(newFileName);
        file.setType(multipartFile.getContentType());
        file.setUpdatedBy(userService.getAuthenticatedUser());
        file.setUrl("/home/apagar/tcc/123.pdf");
        file = fileRepository.save(file);
        switch (type) {
            case DATA_BOOK -> equipment.setDatabookFile(file);
            case SAFETY_JOURNAL -> equipment.setSafetyJournalFile(file);
            case INSTALLATION_PROJECT -> equipment.setInstallationProjectFile(file);
        }
        equipmentRepository.save(equipment);
        var newFile = FileStorageService.NewFile.builder()
                .fileName(file.getName())
                .inputStream(multipartFile.getInputStream())
                .build();
        fileStorageService.replace(oldFileName, newFile);
        return file;
    }

    public File getDocument(Long id, DocumentType type) {
        log.info("Iniciando busca de documento de equipamento id={}, tipo={}", id, type.toString());
        var entity = findOrFail(id);
        switch (type) {
            case DATA_BOOK -> {
                if (ObjectUtils.isEmpty(entity.getDatabookFile())) {
                    throw new FileNotFoundException(id);
                }
                return entity.getDatabookFile();
            }
            case INSTALLATION_PROJECT -> {
                if (ObjectUtils.isEmpty(entity.getInstallationProjectFile())) {
                    throw new FileNotFoundException(id);
                }
                return entity.getInstallationProjectFile();
            }
            case SAFETY_JOURNAL -> {
                if (ObjectUtils.isEmpty(entity.getSafetyJournalFile())) {
                    throw new FileNotFoundException(id);
                }
                return entity.getSafetyJournalFile();
            }
            default -> throw new FileNotFoundException(id);
        }
    }

    @Transactional
    public void deleteDocument(Long id, DocumentType type) throws IOException {
        var equipment = findOrFail(id);
        String filename = null;
        Long fileId = null;
        switch (type) {
            case DATA_BOOK -> {
                if (ObjectUtils.isEmpty(equipment.getDatabookFile())) {
                    throw new BusinessException(String.format("Equipamento de Id=%d não possui prontuário anexado", equipment.getId()));
                }
                filename = equipment.getDatabookFile().getName();
                fileId = equipment.getDatabookFile().getId();
                equipment.setDatabookFile(null);
            }
            case SAFETY_JOURNAL -> {
                if (ObjectUtils.isEmpty(equipment.getSafetyJournalFile())) {
                    throw new BusinessException(String.format("Equipamento de Id=%d não possui registro de segurança anexado", equipment.getId()));
                }
                filename = equipment.getSafetyJournalFile().getName();
                fileId = equipment.getSafetyJournalFile().getId();
                equipment.setSafetyJournalFile(null);
            }
            case INSTALLATION_PROJECT -> {
                if (ObjectUtils.isEmpty(equipment.getInstallationProjectFile())) {
                    throw new BusinessException(String.format("Equipamento de Id=%d não possui projeto de instalação anexado", equipment.getId()));
                }
                filename = equipment.getInstallationProjectFile().getName();
                fileId = equipment.getInstallationProjectFile().getId();
                equipment.setInstallationProjectFile(null);
            }
        }
        equipmentRepository.save(equipment);
        fileRepository.deleteById(fileId);
        fileStorageService.remove(filename);
    }

    private Equipment findOrFail(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
    }

}
