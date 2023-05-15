package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.assembler.DeviceAssembler;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.DeviceSummaryResponse;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import br.com.smartnr.nr13api.domain.service.DeviceService;
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
    private final DeviceService deviceService;
    private final DeviceAssembler deviceAssembler;

    @GetMapping(params = {"plantCode", "tag"})
    public ResponseEntity<List<DeviceSummaryResponse>> findByPlantAndTag(
            @RequestParam(name = "plantCode") String plantCode,
            @RequestParam(name = "tag") String tag) {
        log.info("Recebendo chamada para consulta de Dispositivo código da Planta e Tag");
        return ResponseEntity.ok(deviceAssembler.toSummaryList(deviceService.findTop10ByPlantCodeAndTag(plantCode, tag)));
    }

    @GetMapping("/{id}/calibrations")
    public ResponseEntity<List<CalibrationSummaryResponse>> findLast10ByDeviceId(@PathVariable Long id) {
        log.info("Recebendo chamada para listagem de Calibrações do Dispositivo Id={}", id);
        var entities = calibrationService.findLas10ByDeviceid(id);
        return ResponseEntity.ok(calibrationAssembler.toSummaryList(entities));
    }

}
