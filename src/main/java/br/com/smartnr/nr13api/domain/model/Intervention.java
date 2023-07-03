package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_interventions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportNumber;

    private String executorCompany;

    private String comments;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal cost = BigDecimal.ZERO;

    private LocalDate executionDate;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

}
