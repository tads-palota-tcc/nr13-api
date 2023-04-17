package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long>, JpaSpecificationExecutor<Area> {

    @Query(value = "select * from nr13_api.tb_areas a where a.plant_id in (select plant_id from nr13_api.tb_user_plants p where p.user_id = :userId)", nativeQuery = true)
    List<Area> findByUser(@Param("userId") Long userId);
}
