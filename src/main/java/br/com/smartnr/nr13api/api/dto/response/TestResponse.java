package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.FrequencyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResponse {

    private Long id;
    private String name;
    private String description;
    private Integer frequency;
    private FrequencyType frequencyType;

}
