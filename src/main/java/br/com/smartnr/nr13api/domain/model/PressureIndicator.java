package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pressure_indicators")
public class PressureIndicator extends Device {

    private String gaugeSize;

    private String connectionSize;

    private Double maxGauge;

    private Double minGauge;

}
