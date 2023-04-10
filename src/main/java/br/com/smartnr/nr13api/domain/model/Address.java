package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String streetName;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    @Enumerated(EnumType.STRING)
    private State state;

    private String zipCode;

}
