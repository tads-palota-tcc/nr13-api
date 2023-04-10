package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.PlantCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PlantDetailResponse;
import br.com.smartnr.nr13api.domain.model.Plant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantAssembler {

    private final ModelMapper modelMapper;

    public Plant toEntity(PlantCreationRequest request) {
        return modelMapper.map(request, Plant.class);
    }

    public PlantDetailResponse toDetailResponse(Plant plant) {
        return modelMapper.map(plant, PlantDetailResponse.class);
    }

}
