package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_pressure_safety_valves")
public class PressureSafetyValve extends Device {

    private String size;

    private Double openingPressure;

    private Double closingPressure;

}
