package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long>, JpaSpecificationExecutor<Area> {

    List<Area> findTop10ByCodeContainingIgnoreCaseOrderByCode(String code);

}
