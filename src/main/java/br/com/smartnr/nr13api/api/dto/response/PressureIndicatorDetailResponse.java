package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureIndicatorDetailResponse {

    private Long id;
    private String tag;
    private String description;
    private String manufacturer;
    private String model;
    private String gaugeSize;
    private String connectionSize;
    private Double minGauge;
    private Double maxGauge;
    private PlantSummaryResponse plant;

}
