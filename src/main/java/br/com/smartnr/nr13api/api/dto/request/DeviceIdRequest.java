package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceIdRequest {

    @NotNull
    private Long id;

}
