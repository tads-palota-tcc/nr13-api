package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.core.security.dto.AuthenticationRequest;
import br.com.smartnr.nr13api.core.security.dto.AuthenticationResponse;
import br.com.smartnr.nr13api.core.security.dto.RefreshTokenRequest;
import br.com.smartnr.nr13api.domain.exception.TokenValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    public AuthenticationResponse login(AuthenticationRequest request) {
        log.info("Recebendo chamada para autenticação de usuário");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getEmail());
            return getAuthenticationResponse(user);
        } catch (Exception e) {
            log.error("Erro de autenticação: {}", e.getMessage());
        }
        throw new TokenValidationException("E-mail ou senha inválidos");
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        try {
            var userEmail = jwtService.extractUsername(request.getRefreshToken());
            var user = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(request.getRefreshToken(), user)) {
                return getAuthenticationResponse(user);

            }
        } catch (Exception e) {
            log.error("Erro ao renovar o token: {}", e.getMessage());
        }
        throw new TokenValidationException("Token inválido ou expirado");
    }

    private AuthenticationResponse getAuthenticationResponse(UserDetailsImpl user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("authorities", user.getAuthorities());
        var accessToken = jwtService.generateToken(extraClaims, user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
