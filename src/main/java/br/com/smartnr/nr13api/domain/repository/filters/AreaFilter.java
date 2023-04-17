package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaFilter {

    private Long id;
    private String code;
    private String name;
    private String plantCode;
    private String status;

}
