package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantCreationRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private AddressCreationRequest address;

}
