package br.com.smartnr.nr13api.domain.repository;

import br.com.smartnr.nr13api.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findTop10ByPlantCode(String plantCode);

}
