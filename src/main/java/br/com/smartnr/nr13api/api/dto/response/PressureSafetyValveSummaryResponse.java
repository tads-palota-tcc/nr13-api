package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureSafetyValveSummaryResponse extends DeviceSummaryResponse {

    private String bodySize;
    private Double openingPressure;
    private Double closingPressure;

}
