package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

}
