package br.com.smartnr.nr13api.domain.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_applicable_tests")
public class ApplicableTest {

    @EmbeddedId
    private ApplicableTestPK id;
    private Integer frequency;

    @Enumerated(EnumType.STRING)
    private FrequencyType frequencyType;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    private LocalDate lastTestDate;

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
        return lastTestDate.plusDays(getFrequencyType().getDays());
    }

}
