package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.Device;
import br.com.smartnr.nr13api.domain.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final PlantService plantService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public List<Device> findTop10ByPlantCode(String plantCode) {
        log.info("Iniciando consulta de Dispositivos Planta={}", plantCode);
        return deviceRepository.findTop10ByPlantCode(plantCode);
    }

}
