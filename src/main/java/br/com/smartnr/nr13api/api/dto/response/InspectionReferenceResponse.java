package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionReferenceResponse {

    private Long id;
    private ApplicableTestReferenceResponse applicableTest;

}
