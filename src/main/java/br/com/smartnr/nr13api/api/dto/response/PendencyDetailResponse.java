package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.model.PendencyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PendencyDetailResponse {

    private Long id;
    private UserSummaryResponse author;
    private UserSummaryResponse responsible;
    private String description;
    private String action;
    private LocalDate openedAt;
    private LocalDate deadLine;
    private PendencyType type;
    private PendencyStatus status;
    private InspectionReferenceResponse inspection;

}
