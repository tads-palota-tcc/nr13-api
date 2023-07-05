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
@Table(name = "tb_applicable_tests")
public class ApplicableTest extends BaseEntity<ApplicableTestPK> {

    @EmbeddedId
    private ApplicableTestPK id;

    private Integer frequency;

    @Enumerated(EnumType.STRING)
    private FrequencyType frequencyType;

    private LocalDate lastTestDate;

    private BigDecimal lastCost = BigDecimal.ZERO;

    public Integer getFrequency() {
        if (frequency == null) {
            return id.getTest().getFrequency();
        }
        return this.frequency;
    }

    public FrequencyType getFrequencyType() {
        if (frequencyType == null) {
            return id.getTest().getFrequencyType();
        }
        return frequencyType;
    }

    public LocalDate getNextTestDate() {
        if (lastTestDate == null) return LocalDate.now();
        return switch (getFrequencyType()) {
            case DAY -> lastTestDate.plusDays(getFrequency());
            case MONTH -> lastTestDate.plusMonths(getFrequency());
            case YEAR -> lastTestDate.plusYears(getFrequency());
        };
    }

    public List<LocalDate> getNextTestDatesUntil(LocalDate date) {
        List<LocalDate> dates = new ArrayList<>();
        var nextDate = getNextTestDate();
        while (nextDate.isBefore(date)) {
            dates.add(nextDate);
            nextDate = nextDate.plusDays((long) getFrequencyType().getDays() * getFrequency());
        }
        return dates;
    }

}
