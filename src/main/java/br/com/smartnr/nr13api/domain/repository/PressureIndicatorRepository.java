package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PressureIndicatorRepository extends JpaRepository<PressureIndicator, Long>, JpaSpecificationExecutor<PressureIndicator> {

    @Query("from PressureIndicator pi where pi.plant.id = :plantId and pi.equipment is null")
    List<PressureIndicator> findAvailableByPlant(Long plantId);

    Optional<PressureIndicator> findByTagAndPlantCode(String tag, String code);
}
