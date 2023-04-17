package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.Category;
import br.com.smartnr.nr13api.domain.model.FluidClass;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EquipmentDetailResponse {

    private Long id;
    private String tag;
    private AreaSummaryResponse area;
    private String name;
    private Double hydrostaticTestPressure;
    private Double maxOperationPressure;
    private Double maxAllowedWorkPressure;
    private String manufacturer;
    private String model;
    private String serialNumber;
    private String manufactureYear;
    private String projectCode;
    private String projectCodeEditionYear;
    private Double diameter;
    private Double volume;
    private FluidClass fluidClass;
    private Category category;
    private boolean hasTwoWideExits;
    private boolean hasVentilation;
    private boolean hasMaintenanceAccess;
    private boolean hasLocalLighting;
    private boolean hasEmergencyLighting;
    private boolean hasIdentificationTag;
    private boolean hasCategoryTag;
    private boolean hasDatabook;
    private boolean hasSafetyJournal;
    private boolean hasInstallationProject;
    private List<PressureSafetyValve> pressureSafetyValves;
    private List<PressureIndicator> pressureIndicators;
    private boolean active;

}
