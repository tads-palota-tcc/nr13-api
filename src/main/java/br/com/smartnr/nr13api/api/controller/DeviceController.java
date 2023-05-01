package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.CalibrationAssembler;
import br.com.smartnr.nr13api.api.assembler.PressureIndicatorAssembler;
import br.com.smartnr.nr13api.api.assembler.PressureSafetyValveAssembler;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.DeviceSummaryResponse;
import br.com.smartnr.nr13api.domain.model.DeviceType;
import br.com.smartnr.nr13api.domain.service.CalibrationService;
import br.com.smartnr.nr13api.domain.service.PressureIndicatorService;
import br.com.smartnr.nr13api.domain.service.PressureSafetyValveService;
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

    private final PressureIndicatorService piService;
    private final PressureSafetyValveService psvService;
    private final CalibrationService calibrationService;
    private final PressureIndicatorAssembler piAssembler;
    private final PressureSafetyValveAssembler psvAssembler;
    private final CalibrationAssembler calibrationAssembler;

    @GetMapping(params = {"tag", "plantCode"})
    public ResponseEntity<DeviceSummaryResponse> findByTagAndPlant(
            @RequestParam(name = "tag") String tag,
            @RequestParam(name = "plantCode") String plantCode,
            @RequestParam(name = "type") DeviceType type) {
        log.info("Recebendo chamada para consulta de Dispositivo por Tag, código da Planta e Tipo");
        if (DeviceType.PI.equals(type)) {
            var entity = piService.findByTagAndPlantCode(tag, plantCode);
            return ResponseEntity.ok(piAssembler.toSummaryResponse(entity));
        } else {
            var entity = psvService.findByTagAndPlantCode(tag, plantCode);
            return ResponseEntity.ok(psvAssembler.toSummaryResponse(entity));
        }
    }

    @GetMapping("/{id}/calibrations")
    public ResponseEntity<List<CalibrationSummaryResponse>> findLast10ByDeviceId(@PathVariable Long id) {
        log.info("Recebendo chamada para listagem de Calibrações do Dispositivo Id={}", id);
        var entities = calibrationService.findLas10ByDeviceid(id);
        return ResponseEntity.ok(calibrationAssembler.toSummaryList(entities));
    }
}
