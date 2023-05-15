package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.response.FileResponse;
import br.com.smartnr.nr13api.domain.model.File;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileAssembler {

    private final ModelMapper modelMapper;

    public FileResponse toDetailResponse(File entity) {
        return modelMapper.map(entity, FileResponse.class);
    }
}
