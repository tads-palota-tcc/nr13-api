package br.com.smartnr.nr13api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceSummaryResponse {

    private Long id;
    private String tag;
    private String description;

}
