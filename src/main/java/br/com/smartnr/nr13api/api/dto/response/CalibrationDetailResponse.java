package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CalibrationDetailResponse {

    private Long id;
    private String reportNumber;
    private String executorCompany;
    private String comments;
    private Status status;
    private BigDecimal cost;
    private LocalDate executionDate;
    private LocalDate nextCalibrationDate;
    private DeviceSummaryResponse device;
    private FileResponse file;

}
