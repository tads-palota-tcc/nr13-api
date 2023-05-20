package br.com.smartnr.nr13api.domain.exception;

public class PendencyNotFoundException extends EntityNotFoundException {

    public PendencyNotFoundException(String message) {
        super(message);
    }

    public PendencyNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Pendência com Id=%d", id));
    }
}
