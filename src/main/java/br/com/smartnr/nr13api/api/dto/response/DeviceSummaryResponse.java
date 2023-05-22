package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DeviceSummaryResponse {

    private Long id;
    private String tag;
    private String description;
    private LocalDate lastCalibrationDate;
    private LocalDate nextCalibrationDate;
    private PlantSummaryResponse plant;

}
