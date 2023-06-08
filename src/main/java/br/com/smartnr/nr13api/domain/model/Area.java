package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_areas")
@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Area extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

}
