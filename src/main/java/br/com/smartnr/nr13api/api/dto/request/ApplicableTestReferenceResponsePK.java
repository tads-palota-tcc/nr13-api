package br.com.smartnr.nr13api.api.dto.request;

import br.com.smartnr.nr13api.api.dto.response.EquipmentSummaryResponse;
import br.com.smartnr.nr13api.api.dto.response.TestSummaryResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicableTestReferenceResponsePK {

    private TestSummaryResponse test;
    private EquipmentSummaryResponse equipment;

}
