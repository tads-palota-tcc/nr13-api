package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_devices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class Device extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    private String description;

    private String manufacturer;

    private String model;

    private LocalDate lastCalibrationDate;

    private BigDecimal lastCalibrationCost;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    public LocalDate getNextCalibrationDate() {
        if (this.lastCalibrationDate == null) return LocalDate.now();
        return this.lastCalibrationDate.plusMonths(12);
    }

    public List<LocalDate> getNextCalibrationDatesUntil(LocalDate date) {
        List<LocalDate> dates = new ArrayList<>();
        var nextDate = getNextCalibrationDate();
        while (nextDate.isBefore(date)) {
            dates.add(nextDate);
            nextDate = nextDate.plusMonths(12);
        }
        return dates;
    }

}
