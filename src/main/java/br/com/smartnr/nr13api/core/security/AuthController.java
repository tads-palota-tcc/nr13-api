package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.token.expiration}")
    private Long tokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());
        var authentication = authenticationManager.authenticate(authToken);

        var accessToken = tokenService.generateToken((User) authentication.getPrincipal(), tokenExpiration);
        var refreshToken = tokenService.generateRefreshToken((User) authentication.getPrincipal(), refreshTokenExpiration);

        return ResponseEntity.ok(new TokenDTO(accessToken, tokenExpiration, refreshToken, refreshTokenExpiration));
    }
}
