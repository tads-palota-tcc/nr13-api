package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PressureSafetyValveRepository extends JpaRepository<PressureSafetyValve, Long>, JpaSpecificationExecutor<PressureSafetyValve> {

    @Query("from PressureSafetyValve psv where psv.plant in :plants and psv.equipment is null")
    List<PressureSafetyValve> findAvailableByPlants(List<Plant> plants);

}
