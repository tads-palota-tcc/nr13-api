package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PressureIndicatorSummaryResponse extends DeviceSummaryResponse {

    private String gaugeSize;
    private Double minGauge;
    private Double maxGauge;
    private PlantSummaryResponse plant;
    private LocalDate lastCalibration;
    private Boolean active;

}
