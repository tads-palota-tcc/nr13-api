package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var authentication = authenticationManager.authenticate(authToken);

        var tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJwt));
    }
}
