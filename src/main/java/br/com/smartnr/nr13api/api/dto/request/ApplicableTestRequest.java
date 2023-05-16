package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.FrequencyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicableTestRequest {

    @NotNull
    @Valid
    private TestIdRequest test;

    @Positive
    private Integer frequency;

    private FrequencyType frequencyType;

}
