package br.com.smartnr.nr13api.domain.model;

import lombok.Getter;

@Getter
public enum FrequencyType {
    DAY(1),
    MONTH(30),
    YEAR(365);

    private final Integer days;

    FrequencyType(Integer days) {
        this.days = days;
    }
}
