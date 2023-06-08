package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.*;
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

    private String city;

    @Enumerated(EnumType.STRING)
    private State state;

    @Override
    public int compareTo(Plant plant) {
        return getCode().compareTo(plant.getCode());
    }

}
