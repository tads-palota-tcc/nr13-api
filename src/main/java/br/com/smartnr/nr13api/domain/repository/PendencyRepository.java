package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Pendency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendencyRepository extends JpaRepository<Pendency, Long> {
}
