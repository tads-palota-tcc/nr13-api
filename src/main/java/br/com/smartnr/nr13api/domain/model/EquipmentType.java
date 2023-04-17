package br.com.smartnr.nr13api.domain.model;

import lombok.Getter;

@Getter
public enum EquipmentType {

    BOILER("Caldeira"),
    PRESSURE_VESSEL("Vaso sob pressão"),
    STORAGE_TANK("Tanque de armazenagem"),
    PIPELINE("Tubulação");

    private final String name;

    EquipmentType(String name) {
        this.name = name;
    }
}
