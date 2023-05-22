package br.com.smartnr.nr13api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendenciesByPlant {

    private String plant;
    private Long pendencies;

}
