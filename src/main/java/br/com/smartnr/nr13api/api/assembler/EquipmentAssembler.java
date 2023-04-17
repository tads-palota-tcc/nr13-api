package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.EquipmentCreationRequest;
import br.com.smartnr.nr13api.api.dto.request.EquipmentUpdateRequest;
import br.com.smartnr.nr13api.api.dto.response.EquipmentDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.EquipmentSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EquipmentAssembler {

    private final ModelMapper modelMapper;

    public Equipment toEntity(EquipmentCreationRequest request) {
        return modelMapper.map(request, Equipment.class);
    }

    public Equipment toEntity(EquipmentUpdateRequest request) {
        return modelMapper.map(request, Equipment.class);
    }

    public EquipmentDetailResponse toDetailResponse(Equipment entity) {
        return modelMapper.map(entity, EquipmentDetailResponse.class);
    }

    public Page<EquipmentSummaryResponse> toSummaryPageResponse(Page<Equipment> entities) {
        return entities.map(p -> modelMapper.map(p, EquipmentSummaryResponse.class));
    }

    public List<EquipmentSummaryResponse> toSummaryList(List<Equipment> entities) {
        return entities.stream().map(p -> modelMapper.map(p, EquipmentSummaryResponse.class)).toList();
    }
}
