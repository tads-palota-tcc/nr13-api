package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PressureSafetyValveRepository extends JpaRepository<PressureSafetyValve, Long>, JpaSpecificationExecutor<PressureSafetyValve> {

    @Query("from PressureSafetyValve psv where psv.plant.id = :plantId and psv.equipment is null")
    List<PressureSafetyValve> findAvailableByPlant(Long plantId);

    Optional<PressureSafetyValve> findByTagAndPlantCode(String tag, String plantCode);
}
