package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_inspections")
public class Inspection extends Intervention {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "equipment_id", referencedColumnName = "equipment_id"),
            @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    })
    private ApplicableTest applicableTest;

}
