package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PI")
public class PressureIndicator extends Device {

    private String gaugeSize;

    private String connectionSize;

    private Double maxGauge;

    private Double minGauge;

}
