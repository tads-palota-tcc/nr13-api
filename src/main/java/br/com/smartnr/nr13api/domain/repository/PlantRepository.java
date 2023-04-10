package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
