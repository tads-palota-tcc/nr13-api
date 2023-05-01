package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Calibration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CalibrationRepository extends JpaRepository<Calibration, Long>, JpaSpecificationExecutor<Calibration> {

    List<Calibration> findTop10ByDeviceIdOrderByExecutionDateDesc(Long deviceId);

}
