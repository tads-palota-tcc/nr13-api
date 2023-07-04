package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureIndicatorSummaryResponse extends DeviceSummaryResponse {

    private String gaugeSize;
    private Double minGauge;
    private Double maxGauge;

}
