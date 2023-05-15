package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.AreaNotFoundException;
import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.EntityNotFoundException;
import br.com.smartnr.nr13api.domain.exception.FileNotFoundException;
import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.model.File;
import br.com.smartnr.nr13api.domain.model.Status;
import br.com.smartnr.nr13api.domain.repository.CalibrationRepository;
import br.com.smartnr.nr13api.domain.repository.FileRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalibrationService {

    private final CalibrationRepository calibrationRepository;
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final PlantService plantService;
    private final DeviceService deviceService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public Calibration create(Calibration entity) {
        log.info("Iniciando processo de cadastro de Calibração: {}", entity.getDevice().getId());
        try {
            var device = deviceService.findById(entity.getDevice().getId());
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

    public List<Calibration> findLast10ByDeviceId(Long deviceId) {
        log.info("Iniciando processo de listagem de Calibração pelo Id do dispositivo={}", deviceId);
        return calibrationRepository.findTop10ByDeviceIdOrderByExecutionDateDesc(deviceId);
    }

    public Calibration findById(Long id) {
        log.info("Iniciando busca de Calibração id={}", id);
        return findOrFail(id);
    }

    public File getReportByCalibrationId(Long id) {
        log.info("Iniciando busca de relatório de calibração id={}", id);
        var entity = findOrFail(id);
        if (ObjectUtils.isEmpty(entity.getFile())) {
            throw new FileNotFoundException(id);
        }
        return entity.getFile();
    }

    @Transactional
    public File addReportFile(Long calibrationId, MultipartFile multipartFile) throws IOException {

        var calibration = findOrFail(calibrationId);

        String oldFileName = null;
        if (!ObjectUtils.isEmpty(calibration.getFile())) {
            oldFileName = calibration.getFile().getName();
        }

        String newFileName = fileStorageService.generateFileName(multipartFile.getOriginalFilename());

        var file = new File();
        file.setName(newFileName);
        file.setType(multipartFile.getContentType());
        file.setUpdatedBy(userService.getAuthenticatedUser());
        file.setUrl("/home/apagar/tcc/123.pdf");
        file = fileRepository.save(file);

        calibration.setFile(file);
        calibrationRepository.save(calibration);

        var newFile = FileStorageService.NewFile.builder()
                .fileName(file.getName())
                .inputStream(multipartFile.getInputStream())
                .build();
        fileStorageService.replace(oldFileName, newFile);

        return file;
    }

    private Calibration findOrFail(Long id) {
        return calibrationRepository.findById(id)
                .orElseThrow(() -> new AreaNotFoundException(id));
    }
}
