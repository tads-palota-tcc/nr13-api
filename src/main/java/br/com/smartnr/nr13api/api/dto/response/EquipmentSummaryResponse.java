package br.com.smartnr.nr13api.api.dto.response;

import br.com.smartnr.nr13api.domain.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentSummaryResponse {

    private Long id;
    private String tag;
    private AreaSummaryResponse area;
    private String name;
    private Category category;
    private boolean active;

}
