package br.com.smartnr.nr13api.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailService {

    void send(MailMessage message);

    @Getter
    @Builder
    class MailMessage {
        private Set<String> recipients;
        private String subject;
        private String body;
    }
}
