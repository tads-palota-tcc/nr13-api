package br.com.smartnr.nr13api.core.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String password;

}
