package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureSafetyValveCreationRequest {

    @NotBlank
    private String tag;

    private String description;

    private String manufacturer;

    private String model;

    private String bodySize;

    private Double openingPressure;

    private Double closingPressure;

    @NotNull
    @Valid
    private PlantIdRequest plant;

}
