package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InspectionRepository extends JpaRepository<Inspection, Long>, JpaSpecificationExecutor<Inspection> {
}
