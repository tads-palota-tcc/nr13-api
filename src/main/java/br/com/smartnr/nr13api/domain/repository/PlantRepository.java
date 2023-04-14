package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {

    @Query("select u.plants from User u where u.id = :userId")
    List<Plant> findByUserId(@Param("userId") Long userId);
}
