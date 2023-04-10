package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantDetailResponse {

    private Long id;
    private String code;
    private String name;
    private AddressDetailResponse address;
    private Boolean active;
    private UserIdResponse updatedBy;

}
