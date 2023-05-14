package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PSV")
public class PressureSafetyValve extends Device {

    private String bodySize;

    private Double openingPressure;

    private Double closingPressure;

}
