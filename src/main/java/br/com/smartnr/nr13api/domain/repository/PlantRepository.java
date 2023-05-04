package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {

    List<Plant> findTop10ByCodeContainingIgnoreCaseOrderByCode(String code);
}
