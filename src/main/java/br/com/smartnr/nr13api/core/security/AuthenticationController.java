package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.core.security.dto.AuthenticationRequest;
import br.com.smartnr.nr13api.core.security.dto.RefreshTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request) {
        log.info("Recebendo chamada para autenticação de usuário");
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        log.info("Recebendo chamada para renovação do token de acesso");
        return ResponseEntity.ok(service.refreshToken(request));
    }

}
