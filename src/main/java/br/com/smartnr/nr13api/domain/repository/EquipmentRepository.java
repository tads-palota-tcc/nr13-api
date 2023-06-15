package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Equipment;
import br.com.smartnr.nr13api.domain.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {

    List<Equipment> findTop10ByAreaPlantCodeAndTagContainingIgnoreCaseOrderByTag(String plantCode, String tag);

    List<Equipment> findAllByAreaPlantId(Long plantId);

}
