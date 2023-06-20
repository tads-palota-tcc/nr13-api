package br.com.smartnr.nr13api.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EmailService {

    void send(MailMessage message);

    @Getter
    @Builder
    class MailMessage {

        @Singular
        private Set<String> recipients;

        private String subject;

        private String body;

        @Singular
        private Map<String, Object> variables;

    }
}
