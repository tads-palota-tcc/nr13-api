package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.AreaNotFoundException;
import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.model.DeviceType;
import br.com.smartnr.nr13api.domain.model.Status;
import br.com.smartnr.nr13api.domain.repository.CalibrationRepository;
import br.com.smartnr.nr13api.domain.repository.filters.CalibrationFilter;
import br.com.smartnr.nr13api.domain.repository.specs.CalibrationSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalibrationService {

    private final CalibrationRepository calibrationRepository;
    private final PressureIndicatorService piService;
    private final PressureSafetyValveService psvService;
    private final PlantService plantService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public Calibration create(Calibration entity) {
        log.info("Iniciando processo de cadastro de Calibração: {}", entity.getDevice().getId());
        try {
            var tag = entity.getDevice().getTag();
            var code = entity.getDevice().getPlant().getCode();
            var device = entity.getType().equals(DeviceType.PI) ? piService.findByTagAndPlantCode(tag, code) : psvService.findByTagAndPlantCode(tag, code);
            var userPlants = plantService.findByUser();
            if (!userPlants.contains(device.getPlant())) {
                throw new BusinessException(String
                        .format("Dispositivo com id=%d e Tag=%s não pertence a uma Planta vinculada ao usuário",
                                device.getId(),
                                device.getTag()));
            }
            device.setLastCalibrationDate(entity.getExecutionDate());
            entity.setDevice(device);
            entity.setUpdatedBy(userService.getAuthenticatedUser());
            entity = calibrationRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
        log.info("Cadastro de Calibração realizado com sucesso id={}", entity.getId());
        return entity;
    }

    @Transactional
    public Calibration update(Long id, Calibration entity) {
        log.info("Iniciando processo de atualização de Calibração Id={}", entity.getId());
        if (entity.getStatus().equals(Status.DONE) && ObjectUtils.isEmpty(entity.getReportNumber())) {
            throw new BusinessException("Para status Concluído, o número do relatório deve ser informado");
        }

        if (!ObjectUtils.isEmpty(entity.getReportNumber()) && !entity.getStatus().equals(Status.DONE)) {
            throw new BusinessException("Para calibração com relatório informado, o status deve ser Concluído");
        }
        var existing = this.findOrFail(id);
        modelMapper.map(entity, existing);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        existing.getDevice().setLastCalibrationDate(entity.getExecutionDate());
        calibrationRepository.save(existing);
        return existing;
    }

    public Page<Calibration> findByFilter(CalibrationFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Calibração filtro={}", filter);
        return calibrationRepository.findAll(CalibrationSpecs.withFilter(filter, null), pageable);
    }

    public List<Calibration> findLas10ByDeviceid(Long deviceId) {
        log.info("Iniciando processo de listagem de Calibração pelo Id do dispositivo={}", deviceId);
        return calibrationRepository.findTop10ByDeviceIdOrderByExecutionDateDesc(deviceId);
    }

    public Calibration findById(Long id) {
        log.info("Iniciando busca de Calibração id={}", id);
        return findOrFail(id);
    }

    private Calibration findOrFail(Long id) {
        return calibrationRepository.findById(id)
                .orElseThrow(() -> new AreaNotFoundException(id));
    }
}
