package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.ApplicableTestPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ApplicableTestRepository extends JpaRepository<ApplicableTest, ApplicableTestPK> {

    @Query("select a from ApplicableTest a " +
            "where a.lastTestDate + (a.frequency * (" +
            "   case " +
            "       when a.frequencyType = 'DAY' then 1 " +
            "       when a.frequencyType = 'MONTH' then 30 " +
            "       when a.frequencyType = 'YEAR' then 365 " +
            "   end)) <= :referenceDate " +
            "and a.id.equipment.active = true")
    List<ApplicableTest> findAllOverdue(LocalDate referenceDate);

}
