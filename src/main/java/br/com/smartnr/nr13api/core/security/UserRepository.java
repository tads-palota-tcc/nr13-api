package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
