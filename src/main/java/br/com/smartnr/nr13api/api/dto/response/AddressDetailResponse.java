package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDetailResponse {

    private String streetName;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private State state;
    private String zipCode;

}
