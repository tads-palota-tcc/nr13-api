package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.ApplicableTest;
import br.com.smartnr.nr13api.domain.model.ApplicableTestPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicableTestRepository extends JpaRepository<ApplicableTest, ApplicableTestPK> {

}
