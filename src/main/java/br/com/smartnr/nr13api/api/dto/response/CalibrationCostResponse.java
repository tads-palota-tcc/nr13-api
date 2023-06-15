package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CalibrationCostResponse {

    private String type;
    private LocalDate referenceDate;
    private BigDecimal cost;

}
