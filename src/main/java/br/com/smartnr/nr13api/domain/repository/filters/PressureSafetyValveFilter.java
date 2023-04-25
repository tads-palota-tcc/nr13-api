package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PressureSafetyValveFilter {

    private Long id;
    private String tag;
    private String bodySize;
    private Double openingPressure;
    private Double closingPressure;
    private String plantCode;
    private String status;

}
