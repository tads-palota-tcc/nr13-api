package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.domain.model.PendencyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PendencyCreationRequest {

    @NotNull
    @Valid
    private UserIdRequest responsible;

    @NotBlank
    private String description;

    @NotBlank
    private String action;

    @NotNull
    @FutureOrPresent
    private LocalDate deadLine;

    @NotNull
    private PendencyType type;

    @NotNull
    @Valid
    private InspectionIdRequest inspection;

}
