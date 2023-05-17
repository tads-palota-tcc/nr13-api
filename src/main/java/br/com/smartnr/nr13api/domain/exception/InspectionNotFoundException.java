package br.com.smartnr.nr13api.domain.exception;

public class InspectionNotFoundException extends EntityNotFoundException {

    public InspectionNotFoundException(String message) {
        super(message);
    }

    public InspectionNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Inspeção com Id=%d", id));
    }
}
