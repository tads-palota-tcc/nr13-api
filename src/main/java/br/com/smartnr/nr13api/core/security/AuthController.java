package br.com.smartnr.nr13api.core.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final AuthenticationService authenticationService;

    @Value("${jwt.token.expiration}")
    private Long tokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());
        var authentication = authenticationManager.authenticate(authToken);
        var accessToken = tokenService.generateToken((UserDetailsImpl) authentication.getPrincipal(), tokenExpiration);
        var refreshToken = tokenService.generateRefreshToken((UserDetailsImpl) authentication.getPrincipal(), refreshTokenExpiration);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", refreshToken).path("/auth").maxAge(24 * 60 * 60).httpOnly(true).build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new TokenResponse(accessToken, tokenExpiration));
    }

    @PostMapping("refresh-token")
    public ResponseEntity refreshToken(@RequestBody @Valid RefreshTokenRequest dto) {
        var user = authenticationService.loadUserByUsername(tokenService.getSubject(dto.getRefreshToken()));
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        var accessToken = tokenService.generateToken((UserDetailsImpl) authentication.getPrincipal(), tokenExpiration);
        return ResponseEntity.ok().body(new TokenResponse(accessToken, tokenExpiration));
    }
}
