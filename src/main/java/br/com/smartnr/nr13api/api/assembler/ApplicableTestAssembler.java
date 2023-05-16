package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.request.ApplicableTestRequest;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicableTestAssembler {

    private final ModelMapper modelMapper;

    public ApplicableTest toEntity(ApplicableTestRequest request) {
        return modelMapper.map(request, ApplicableTest.class);
    }

}
