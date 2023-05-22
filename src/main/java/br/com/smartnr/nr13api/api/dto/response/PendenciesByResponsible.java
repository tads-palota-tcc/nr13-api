package br.com.smartnr.nr13api.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendenciesByResponsible {

    private String responsible;
    private Long pendencies;

}
