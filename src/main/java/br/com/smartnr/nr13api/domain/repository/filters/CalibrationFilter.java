package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalibrationFilter {

    private LocalDate executionDate;
    private String reportNumber;
    private String executorCompany;
    private String status;

}
