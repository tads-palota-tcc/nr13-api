package br.com.smartnr.nr13api.domain.repository.filters;

import br.com.smartnr.nr13api.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentFilter {

    private Long id;
    private String tag;
    private String name;
    private String areaCode;
    private String status;

}
