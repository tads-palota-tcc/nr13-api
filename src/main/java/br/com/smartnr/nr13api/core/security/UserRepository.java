package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findTop10ByNameContainingIgnoreCaseOrderByName(String name);

}
