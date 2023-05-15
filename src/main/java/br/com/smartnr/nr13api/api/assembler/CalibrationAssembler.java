package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.CalibrationCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.CalibrationDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.CalibrationSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Calibration;
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
        return modelMapper.map(request, Calibration.class);
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
