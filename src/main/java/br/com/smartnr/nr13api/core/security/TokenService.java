package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.exception.TokenValidationException;
import br.com.smartnr.nr13api.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user, Long seconds) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withExpiresAt(expirationDate(seconds))
                    .withIssuedAt(new Date())
                    .withSubject(user.getUsername())
                    .withClaim("name", user.getName())
                    .withClaim("authorities", getAuthorities(user.getAuthorities()))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token");
        }
    }

    public String generateRefreshToken(User user, Long seconds) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withExpiresAt(expirationDate(seconds))
                    .withIssuedAt(new Date())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar refresh-token");
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build().verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new TokenValidationException("Token inválido ou expirado");
        }
    }

    private List<String> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private Instant expirationDate(Long seconds) {
        return LocalDateTime.now().plusSeconds(seconds).toInstant(ZoneOffset.of("-03:00"));
    }

}
