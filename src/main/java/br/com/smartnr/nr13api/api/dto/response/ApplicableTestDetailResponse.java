package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.FrequencyType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ApplicableTestDetailResponse {

    private TestSummaryResponse test;
    private EquipmentSummaryResponse equipment;
    private Integer frequency;
    private FrequencyType frequencyType;
    private LocalDate lastTestDate;
    private LocalDate nextTestDate;
    private Boolean active;

}
