package br.com.smartnr.nr13api.domain.exception;

public class TestNotFoundException extends EntityNotFoundException {

    public TestNotFoundException(String message) {
        super(message);
    }

    public TestNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Teste com Id=%d", id));
    }
}
