package br.com.smartnr.nr13api.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    INVALID_DATA("/indalid-data", "Dados inválids"),
    SYSTEM_ERROR("/system-error", "Erro do sistema"),
    INVALID_PARAMETER("/invalid-parameter", "Parâmetro inválido"),
    INCOMPREHENSIVE_MESSAGE("/incomprehensive-message", "Mensagem imcompreensível"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
    ENTITY_IN_USE("/entity-in-use", "Entidade em uso"),
    AUTHENTICATION_ERROR("/authentication-error", "Erro de autenticação"),
    BUSINESS_ERROR("/business-error", "Erro de negócio");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "http://mtmanager.com.br" + path;
        this.title = title;
    }
}
