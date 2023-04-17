package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.Category;
import br.com.smartnr.nr13api.domain.model.FluidClass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EquipmentUpdateRequest {

    @NotBlank
    private String tag;

    @NotNull
    @Valid
    private AreaIdRequest area;

    @NotBlank
    private String name;

    @NotNull
    private Double hydrostaticTestPressure;

    @NotNull
    private Double maxOperationPressure;

    @NotNull
    private Double maxAllowedWorkPressure;

    private String manufacturer;

    private String model;

    private String serialNumber;

    private String manufactureYear;

    private String projectCode;

    private String projectCodeEditionYear;

    @NotNull
    @Positive
    private Double diameter;

    @NotNull
    private Double volume;

    @NotNull
    private FluidClass fluidClass;

    private Category category;

    private boolean active = true;

    private boolean hasTwoWideExits = false;

    private boolean hasVentilation = false;

    private boolean hasMaintenanceAccess = false;

    private boolean hasLocalLighting = false;

    private boolean hasEmergencyLighting = false;

    private boolean hasIdentificationTag = false;

    private boolean hasCategoryTag = false;

    private boolean hasDatabook = false;

    private boolean hasSafetyJournal = false;

    private boolean hasInstallationProject = false;

}
