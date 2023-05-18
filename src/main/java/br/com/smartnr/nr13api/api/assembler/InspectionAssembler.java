package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.InspectionCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.InspectionSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Inspection;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InspectionAssembler {

    private final ModelMapper modelMapper;

    public Inspection toEntity(InspectionCreationRequest request) {
        return modelMapper.map(request, Inspection.class);
    }

    public InspectionSummaryResponse toSummaryResponse(Inspection entity) {
        return modelMapper.map(entity, InspectionSummaryResponse.class);
    }

    public Page<InspectionSummaryResponse> toSummaryPageResponse(Page<Inspection> entities) {
        return entities.map(p -> modelMapper.map(p, InspectionSummaryResponse.class));
    }

    public List<InspectionSummaryResponse> toSummaryList(List<Inspection> entities) {
        return entities.stream().map(p -> modelMapper.map(p, InspectionSummaryResponse.class)).toList();
    }
}
