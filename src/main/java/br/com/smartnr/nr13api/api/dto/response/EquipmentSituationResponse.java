package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentSituationResponse {

    private String tag;
    private String plant;
    private Integer expiredInspections;
    private Integer nextToExpireInspections;
    private Integer expiredCalibrations;
    private Integer nextToExpireCalibrations;

}
