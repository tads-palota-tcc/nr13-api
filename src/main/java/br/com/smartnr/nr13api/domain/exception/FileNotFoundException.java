package br.com.smartnr.nr13api.domain.exception;

public class FileNotFoundException extends EntityNotFoundException {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(Long id) {
        this(String.format("Não existe um arquivo com Id=%d", id));
    }
}
