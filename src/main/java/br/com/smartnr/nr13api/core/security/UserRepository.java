package br.com.smartnr.nr13api.core.security;

import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findTop10ByNameContainingIgnoreCaseOrderByName(String name);

    @Query("select distinct u from User u where :plant member of u.plants and u.active = true")
    List<User> findAllByPlant(Plant plant);

}
