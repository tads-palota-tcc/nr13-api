package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.PressureSafetyValveCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PressureSafetyValveDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.PressureSafetyValveSummaryResponse;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PressureSafetyValveAssembler {

    private final ModelMapper modelMapper;

    public PressureSafetyValve toEntity(PressureSafetyValveCreationRequest request) {
        return modelMapper.map(request, PressureSafetyValve.class);
    }

    public PressureSafetyValveDetailResponse toDetailResponse(PressureSafetyValve entity) {
        return modelMapper.map(entity, PressureSafetyValveDetailResponse.class);
    }

    public Page<PressureSafetyValveSummaryResponse> toSummaryPageResponse(Page<PressureSafetyValve> entities) {
        return entities.map(p -> modelMapper.map(p, PressureSafetyValveSummaryResponse.class));
    }

    public PressureSafetyValveSummaryResponse toSummaryResponse(PressureSafetyValve entity) {
        return modelMapper.map(entity, PressureSafetyValveSummaryResponse.class);
    }

    public List<PressureSafetyValveSummaryResponse> toSummaryList(List<PressureSafetyValve> entities) {
        return entities.stream().map(p -> modelMapper.map(p, PressureSafetyValveSummaryResponse.class)).toList();
    }
}
