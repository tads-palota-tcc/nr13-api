package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.PressureIndicatorCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PressureIndicatorDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.PressureIndicatorSummaryResponse;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PressureIndicatorAssembler {

    private final ModelMapper modelMapper;

    public PressureIndicator toEntity(PressureIndicatorCreationRequest request) {
        return modelMapper.map(request, PressureIndicator.class);
    }

    public PressureIndicatorDetailResponse toDetailResponse(PressureIndicator entity) {
        return modelMapper.map(entity, PressureIndicatorDetailResponse.class);
    }

    public Page<PressureIndicatorSummaryResponse> toSummaryPageResponse(Page<PressureIndicator> entities) {
        return entities.map(p -> modelMapper.map(p, PressureIndicatorSummaryResponse.class));
    }

    public List<PressureIndicatorSummaryResponse> toSummaryList(List<PressureIndicator> entities) {
        return entities.stream().map(p -> modelMapper.map(p, PressureIndicatorSummaryResponse.class)).toList();
    }
}
