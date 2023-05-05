package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureIndicatorCreationRequest {

    @NotBlank
    private String tag;

    private String description;

    private String manufacturer;

    private String model;

    private String gaugeSize;

    private String connectionSize;

    private Double maxGauge;

    private Double minGauge;

    @NotNull
    @Valid
    private PlantIdRequest plant;

}
