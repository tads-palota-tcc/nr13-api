package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.EquipmentNotFoundException;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.Equipment;
import br.com.smartnr.nr13api.domain.repository.EquipmentRepository;
import br.com.smartnr.nr13api.domain.repository.filters.EquipmentFilter;
import br.com.smartnr.nr13api.domain.repository.specs.EquipmentSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Equipment findOrFail(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
    }
}
