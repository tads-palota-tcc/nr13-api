package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantForCalibrationRequest {

    @NotBlank
    private String code;

}
