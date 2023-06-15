package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
public class CostForecastResponse {

    private Month month;
    private Integer year;
    private BigDecimal inspectionCost = BigDecimal.ZERO;
    private BigDecimal calibrationCost = BigDecimal.ZERO;

}
