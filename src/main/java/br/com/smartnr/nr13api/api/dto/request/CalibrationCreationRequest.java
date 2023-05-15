package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CalibrationCreationRequest {

    private String reportNumber;

    @NotBlank
    private String executorCompany;

    private String comments;

    @NotNull
    private Status status;

    @NotNull
    @PositiveOrZero
    private BigDecimal cost;

    @NotNull
    @PastOrPresent
    private LocalDate executionDate;

    @NotNull
    @Valid
    private DeviceIdRequest device;

}
