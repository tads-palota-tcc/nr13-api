package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.core.security.UserDetailsImpl;
import br.com.smartnr.nr13api.core.security.UserRepository;
import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> findTop10(String name) {
        log.info("Iniciando processo de listagem dos 10 primeiros Usuários com nome contendo {}", name);
        return userRepository.findTop10ByNameContainingIgnoreCaseOrderByName(name);
    }

    public List<User> findAllByPlant(Plant plant) {
        log.info("Iniciando processo de listagem de Usuários por Planta id={}", plant.getId());
        return userRepository.findAllByPlant(plant);
    }

    private User findOrFail(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

}
