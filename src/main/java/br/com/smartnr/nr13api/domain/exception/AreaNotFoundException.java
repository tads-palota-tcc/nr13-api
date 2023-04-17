package br.com.smartnr.nr13api.domain.exception;

public class AreaNotFoundException extends EntityNotFoundException {

    public AreaNotFoundException(String message) {
        super(message);
    }

    public AreaNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Área com Id=%d", id));
    }
}
