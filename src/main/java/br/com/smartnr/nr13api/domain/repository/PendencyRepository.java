package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Pendency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PendencyRepository extends JpaRepository<Pendency, Long>, JpaSpecificationExecutor<Pendency> {
}
