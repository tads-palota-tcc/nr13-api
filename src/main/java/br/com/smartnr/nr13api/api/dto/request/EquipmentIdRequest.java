package br.com.smartnr.nr13api.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentIdRequest {

    @NotNull
    private Long id;

}
