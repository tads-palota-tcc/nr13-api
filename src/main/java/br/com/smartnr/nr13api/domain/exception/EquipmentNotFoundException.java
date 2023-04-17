package br.com.smartnr.nr13api.domain.exception;

public class EquipmentNotFoundException extends EntityNotFoundException {

    public EquipmentNotFoundException(String message) {
        super(message);
    }

    public EquipmentNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Equipamento com Id=%d", id));
    }
}
