package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalibrationFilter {

    private Long id;
    private String reportNumber;
    private String calibrationStatus;
    private LocalDate executionDate;
    private String deviceTag;
    private String status;

}
