package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PressureIndicatorRepository extends JpaRepository<PressureIndicator, Long>, JpaSpecificationExecutor<PressureIndicator> {

    @Query("from PressureIndicator pi where pi.plant in :plants and pi.equipment is null")
    List<PressureIndicator> findAvailableByPlants(List<Plant> plants);
}
