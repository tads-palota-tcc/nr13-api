package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findTop10ByPlantCodeAndTagContainingIgnoreCaseOrderByTag(String plantCode, String tag);

    @Query("select d from Device d where d.lastCalibrationDate <= :referenceDate and d.active = true")
    List<Device> findAllOverdue(LocalDate referenceDate);

}
