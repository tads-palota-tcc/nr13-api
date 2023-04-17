package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.AreaCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.AreaDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.AreaSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Area;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AreaAssembler {

    private final ModelMapper modelMapper;

    public Area toEntity(AreaCreationRequest request) {
        return modelMapper.map(request, Area.class);
    }

    public AreaDetailResponse toDetailResponse(Area entity) {
        return modelMapper.map(entity, AreaDetailResponse.class);
    }

    public Page<AreaSummaryResponse> toSummaryPageResponse(Page<Area> entities) {
        return entities.map(p -> modelMapper.map(p, AreaSummaryResponse.class));
    }

    public List<AreaSummaryResponse> toSummaryList(List<Area> entities) {
        return entities.stream().map(p -> modelMapper.map(p, AreaSummaryResponse.class)).toList();
    }
}
