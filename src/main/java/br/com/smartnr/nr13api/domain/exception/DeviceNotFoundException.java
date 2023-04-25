package br.com.smartnr.nr13api.domain.exception;

public class DeviceNotFoundException extends EntityNotFoundException {

    public DeviceNotFoundException(String message) {
        super(message);
    }

    public DeviceNotFoundException(Long id) {
        this(String.format("Não existe um cadastro de Dispositivo com Id=%d", id));
    }
}
