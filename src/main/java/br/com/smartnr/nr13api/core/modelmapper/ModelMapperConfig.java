package br.com.smartnr.nr13api.core.modelmapper;

import br.com.smartnr.nr13api.api.dto.response.ApplicableTestResponse;
import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        applicableTestsMappingConfig(modelMapper);

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    private void applicableTestsMappingConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(ApplicableTest.class, ApplicableTestResponse.class)
                .addMapping(at -> (at.getId().getTest().getName()), ApplicableTestResponse::setName)
                .addMapping(at -> (at.getId().getTest().getId()), ApplicableTestResponse::setTestId);
    }
}
