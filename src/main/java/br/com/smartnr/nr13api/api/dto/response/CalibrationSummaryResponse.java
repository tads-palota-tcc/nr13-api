package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalibrationSummaryResponse {

    private Long id;
    private String reportNumber;
    private Status status;
    private LocalDate executionDate;
    private DeviceSummaryResponse device;

}
