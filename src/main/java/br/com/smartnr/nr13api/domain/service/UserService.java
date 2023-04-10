package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.User;
import br.com.smartnr.nr13api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        log.info("Iniciando processo de consulta de Usuário por id={}", id);
        var user = findOrFail(id);
        log.info("consulta de Usuário realizada com sucesso id={}", id);
        return user;
    }

    private User findOrFail(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Usuário com id=%d não encontrado", id)));
    }

    public User getAuthenticatedUser() {
        String username = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaimAsString("preferred_username");
        return findOrFail(Long.parseLong(username));
    }

}
