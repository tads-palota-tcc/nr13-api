package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.PendencyCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PendencyDetailResponse;
import br.com.smartnr.nr13api.domain.model.Pendency;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendencyAssembler {

    private final ModelMapper modelMapper;

    public Pendency toEntity(PendencyCreationRequest request) {
        return modelMapper.map(request, Pendency.class);
    }

    public PendencyDetailResponse toDetailResponse(Pendency entity) {
        return modelMapper.map(entity, PendencyDetailResponse.class);
    }

    public Page<PendencyDetailResponse> toPageResponse(Page<Pendency> entities) {
        return entities.map(p -> modelMapper.map(p, PendencyDetailResponse.class));
    }

    public List<PendencyDetailResponse> toList(List<Pendency> entities) {
        return entities.stream().map(p -> modelMapper.map(p, PendencyDetailResponse.class)).toList();
    }
}
