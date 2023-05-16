package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestIdRequest {

    @NotNull
    private Long id;

}
