package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Pendency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PendencyRepository extends JpaRepository<Pendency, Long>, JpaSpecificationExecutor<Pendency> {

    List<Pendency> findAllByInspectionIdOrderByDeadLineDesc(Long inspectionId);

    @Query("select p from Pendency p where p.status != 'COMPLETED' and p.deadLine <= :referenceDate order by p.deadLine asc")
    List<Pendency> findAllOverdue(LocalDate referenceDate);

}
