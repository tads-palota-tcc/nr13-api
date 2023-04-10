package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressCreationRequest {

    private String streetName;

    private String number;

    private String complement;

    private String neighborhood;

    @NotBlank
    private String city;

    @NotNull
    private State state;

    private String zipCode;

}
