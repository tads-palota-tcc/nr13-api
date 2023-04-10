package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantSummaryResponse {

    private Long id;
    private String code;
    private String name;
    private Boolean active;

}
