package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {

    List<Equipment> findTop10ByAreaPlantCodeAndTagContainingIgnoreCaseOrderByTag(String plantCode, String tag);

    List<Equipment> findAllByAreaPlantId(Long plantId);

    @Query("select e from Equipment e where e.active = 'true' and ((:plantId is NULL) or e.area.plant.id = :plantId)")
    Page<Equipment> findAllByAreaPlantId(Long plantId, Pageable pageable);

}
