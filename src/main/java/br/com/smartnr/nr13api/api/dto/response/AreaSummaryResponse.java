package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaSummaryResponse {

    private Long id;
    private String code;
    private String name;
    private PlantSummaryResponse plant;
    private Boolean active;

}
