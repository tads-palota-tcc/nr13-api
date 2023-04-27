package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.CalibrationCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.DeviceIdRequest;
import br.com.smartnr.nr13api.api.dto.request.PressureIndicatorIdRequest;
import br.com.smartnr.nr13api.api.dto.request.PressureSafetyValveIdRequest;
import br.com.smartnr.nr13api.api.dto.response.CalibrationDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.model.DeviceType;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CalibrationAssembler {

    private final ModelMapper modelMapper;

    public Calibration toEntity(CalibrationCreationRequest request) {
        var entity = new Calibration();
        entity.setType(request.getType());
        entity.setReportNumber(request.getReportNumber());
        entity.setExecutorCompany(request.getExecutorCompany());
        entity.setComments(request.getComments());
        entity.setStatus(request.getStatus());
        entity.setCost(request.getCost());
        entity.setExecutionDate(request.getExecutionDate());
        if (request.getType().equals(DeviceType.PI)) {
            var device = new PressureIndicator();
            device.setId(request.getDevice().getId());
            entity.setDevice(device);
        } else {
            var device = new PressureSafetyValve();
            device.setId(request.getDevice().getId());
            entity.setDevice(device);
        }
        return entity;
    }

    public CalibrationDetailResponse toDetailResponse(Calibration entity) {
        return modelMapper.map(entity, CalibrationDetailResponse.class);
    }

    public Page<CalibrationSummaryResponse> toSummaryPageResponse(Page<Calibration> entities) {
        return entities.map(p -> modelMapper.map(p, CalibrationSummaryResponse.class));
    }

    public List<CalibrationSummaryResponse> toSummaryList(List<Calibration> entities) {
        return entities.stream().map(p -> modelMapper.map(p, CalibrationSummaryResponse.class)).toList();
    }
}
