package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.core.security.UserDetailsImpl;
import br.com.smartnr.nr13api.core.security.UserRepository;
import br.com.smartnr.nr13api.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        log.info("Iniciando busca de Usuário pelo id={}", id);
        return findOrFail(id);
    }

    public User getAuthenticatedUser() {
        var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findOrFail(userDetails.getId());
    }

    private User findOrFail(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

}
