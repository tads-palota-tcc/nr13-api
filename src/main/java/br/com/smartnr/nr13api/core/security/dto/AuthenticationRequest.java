package br.com.smartnr.nr13api.core.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
