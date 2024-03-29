package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CalibrationSummaryResponse {

    private Long id;
    private LocalDate executionDate;
    private String reportNumber;
    private String executorCompany;
    private Status status;
    private FileUrlResponse file;
    private DeviceSummaryResponse device;

}
