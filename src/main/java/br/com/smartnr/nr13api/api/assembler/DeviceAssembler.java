package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.response.DeviceSummaryResponse;
import br.com.smartnr.nr13api.domain.model.Device;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceAssembler {

    private final ModelMapper modelMapper;

    public List<DeviceSummaryResponse> toSummaryList(List<Device> entities) {
        return entities.stream().map(p -> modelMapper.map(p, DeviceSummaryResponse.class)).toList();
    }
}
