package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.core.security.dto.AuthenticationRequest;
import br.com.smartnr.nr13api.core.security.dto.AuthenticationResponse;
import br.com.smartnr.nr13api.domain.exception.TokenValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getEmail());
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("authorities", user.getAuthorities());
        var jwt = jwtService.generateToken(extraClaims, user);
        var response = AuthenticationResponse
                .builder()
                .accessToken(jwt)
                .build();

        var refreshToken = jwtService.generateToken(user);
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from("refresh-token", refreshToken)
                .path("/auth")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@CookieValue("refresh-token") String token) {
        try {
            var userEmail = jwtService.extractUsername(token);
            var user = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(token, user)) {
                var jwt = jwtService.generateRefreshToken(user);
                var response = AuthenticationResponse
                        .builder()
                        .accessToken(jwt)
                        .build();
                return ResponseEntity.ok()
                        .body(response);
            }
        } catch (Exception e) {
            throw new TokenValidationException("Erro ao gerar novo token");
        }
        throw new TokenValidationException("Erro ao gerar novo token");

    }

}
