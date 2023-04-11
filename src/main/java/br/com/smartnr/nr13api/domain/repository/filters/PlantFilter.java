package br.com.smartnr.nr13api.domain.repository.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantFilter {

    private Long id;
    private String code;
    private String name;
    private String status;

}
