package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureIndicatorFilter {

    private Long id;
    private String tag;
    private Double minGauge;
    private Double maxGauge;
    private String plantCode;
    private String status;

}
