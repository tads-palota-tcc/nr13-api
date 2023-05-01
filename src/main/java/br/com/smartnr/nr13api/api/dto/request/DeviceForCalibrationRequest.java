package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceForCalibrationRequest {

    @NotBlank
    private String tag;

    @NotNull
    @Valid
    private PlantForCalibrationRequest plant;

}
