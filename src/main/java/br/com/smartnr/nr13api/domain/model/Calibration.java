package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_calibrations")
public class Calibration extends Intervention {

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

}
