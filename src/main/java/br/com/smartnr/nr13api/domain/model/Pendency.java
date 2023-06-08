package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_pendencies")
@Data
@EqualsAndHashCode(of = {"id"})
public class Pendency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "responsible")
    private User responsible;

    private String description;

    private String action;

    private BigDecimal cost;

    private LocalDate openedAt;

    private LocalDate deadLine;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Enumerated(EnumType.STRING)
    private PendencyType type;

    @Enumerated(EnumType.STRING)
    private PendencyStatus status;

    @ManyToOne
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

}
