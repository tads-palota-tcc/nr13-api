package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.DeviceNotFoundException;
import br.com.smartnr.nr13api.domain.model.Device;
import br.com.smartnr.nr13api.domain.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public List<Device> findTop10ByPlantCodeAndTag(String plantCode, String tag) {
        log.info("Iniciando consulta de Dispositivos por Planta={} e Tag={}", plantCode, tag);
        return deviceRepository.findTop10ByPlantCodeAndTagContainingIgnoreCaseOrderByTag(plantCode, tag);
    }

    public Device findById(Long id) {
        return findOrFail(id);
    }

    private Device findOrFail(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }

}
