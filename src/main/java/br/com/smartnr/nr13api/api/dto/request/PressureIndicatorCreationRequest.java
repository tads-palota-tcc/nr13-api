package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.Equipment;
import br.com.smartnr.nr13api.domain.model.Plant;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Valid
    private EquipmentIdRequest equipment;

    @NotNull
    @Valid
    private PlantIdRequest plant;

    private Boolean active = Boolean.TRUE;

}
