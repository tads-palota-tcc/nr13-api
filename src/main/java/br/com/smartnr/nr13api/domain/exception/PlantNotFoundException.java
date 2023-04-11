package br.com.smartnr.nr13api.domain.exception;

public class PlantNotFoundException extends EntityNotFoundException {

    public PlantNotFoundException(String message) {
        super(message);
    }

    public PlantNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Planta com Id=%d", id));
    }
}
