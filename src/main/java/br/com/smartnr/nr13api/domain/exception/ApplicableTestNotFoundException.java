package br.com.smartnr.nr13api.domain.exception;

public class ApplicableTestNotFoundException extends EntityNotFoundException {

    public ApplicableTestNotFoundException(String message) {
        super(message);
    }

    public ApplicableTestNotFoundException(Long testId, Long equipmentId) {
        this(String.format("Não existe um cadastro de Teste com Id=%d para o equipamento com Id=%d", testId, equipmentId));
    }
}
