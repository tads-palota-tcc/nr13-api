package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureSafetyValveDetailResponse {

    private Long id;
    private String tag;
    private String description;
    private String manufacturer;
    private String model;
    private String bodySize;
    private Double openingPressure;
    private Double closingPressure;
    private PlantSummaryResponse plant;

}
