package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_plants")
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Plant extends BaseEntity<Long> implements Comparable<Plant> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @Embedded
    private Address address;

    @Override
    public int compareTo(Plant plant) {
        return getCode().compareTo(plant.getCode());
    }
}
