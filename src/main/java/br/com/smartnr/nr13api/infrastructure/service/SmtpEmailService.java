package br.com.smartnr.nr13api.infrastructure.service;

import br.com.smartnr.nr13api.domain.exception.EmailExceptionException;
import br.com.smartnr.nr13api.domain.service.EmailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    @Value("${smartnr.email.sender}")
    private String sender;

    @Override
    public void send(MailMessage message) {
        try {
            String body = processTemplate(message);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setFrom(sender);
            helper.setSubject(message.getSubject());
            helper.setText(body, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailExceptionException("Não foi possível enviar e-mail");
        }
    }

    private String processTemplate(MailMessage message) throws IOException {
        try {
            var template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailExceptionException("Não foi possível montar o template do e-mail");
        }
    }
}
