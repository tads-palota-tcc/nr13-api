package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.InspectionNotFoundException;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.Inspection;
import br.com.smartnr.nr13api.domain.repository.ApplicableTestRepository;
import br.com.smartnr.nr13api.domain.repository.InspectionRepository;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.repository.specs.InspectionSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final TestService testService;
    private final UserService userService;
    private final ApplicableTestRepository applicableTestRepository;
    private final EquipmentService equipmentService;

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
}
