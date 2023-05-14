package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.assembler.DeviceAssembler;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.DeviceSummaryResponse;
import br.com.smartnr.nr13api.domain.repository.DeviceRepository;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final CalibrationService calibrationService;
    private final CalibrationAssembler calibrationAssembler;
    private final DeviceRepository deviceRepository;
    private final DeviceAssembler deviceAssembler;

    @GetMapping(params = {"plantCode"})
    public ResponseEntity<List<DeviceSummaryResponse>> findByPlant(@RequestParam(name = "plantCode") String plantCode) {
        log.info("Recebendo chamada para consulta de Dispositivo código da Planta");
        return ResponseEntity.ok(deviceAssembler.toSummaryList(deviceRepository.findTop10ByPlantCode(plantCode)));
    }

    @GetMapping("/{id}/calibrations")
    public ResponseEntity<List<CalibrationSummaryResponse>> findLast10ByDeviceId(@PathVariable Long id) {
        log.info("Recebendo chamada para listagem de Calibrações do Dispositivo Id={}", id);
        var entities = calibrationService.findLas10ByDeviceid(id);
        return ResponseEntity.ok(calibrationAssembler.toSummaryList(entities));
    }

}
