package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.model.Device;
import br.com.smartnr.nr13api.domain.model.DeviceType;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import br.com.smartnr.nr13api.domain.repository.CalibrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalibrationService {

    private final CalibrationRepository calibrationRepository;
    private final PressureIndicatorService piService;
    private final PressureSafetyValveService psvService;
    private final PlantService plantService;
    private final UserService userService;

    @Transactional
    public Calibration create(Calibration entity) {
        log.info("Iniciando processo de cadastro de Calibração: {}", entity.getDevice().getId());
        try {
            var deviceId = entity.getDevice().getId();
            var device = entity.getType().equals(DeviceType.PI) ? piService.findById(deviceId) : psvService.findById(deviceId);
            var userPlants = plantService.findByUser();
            if (!userPlants.contains(device.getPlant())) {
                throw new BusinessException(String
                        .format("Dispositivo com id=%d e Tag=%s não pertence a uma Planta vinculada ao usuário",
                                device.getId(),
                                device.getTag()));
            }
            entity.setDevice(device);
            entity.setUpdatedBy(userService.getAuthenticatedUser());
            entity = calibrationRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
        log.info("Cadastro de Calibração realizado com sucesso id={}", entity.getId());
        return entity;
    }
}
