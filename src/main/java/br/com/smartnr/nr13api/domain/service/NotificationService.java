package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.User;
import br.com.smartnr.nr13api.domain.repository.PendencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final PendencyRepository pendencyRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Value("${pendency.days.to.expiration.alert}")
    private Long daysToExpirationAlert;

    public int notifyPendencies() {
        Map<User, List<Pendency>> pendenciesByUser = new HashMap<>();

        pendencyRepository.findAllOverdue(LocalDate.now().plusDays(daysToExpirationAlert)).forEach(p -> {
            if (!pendenciesByUser.containsKey(p.getResponsible())) {
                pendenciesByUser.put(p.getResponsible(), new ArrayList<>());
            }
            pendenciesByUser.get(p.getResponsible()).add(p);
        });

        pendenciesByUser.forEach((k, v) -> {
            emailService.send(EmailService.MailMessage.builder()
                            .subject(v.get(0).getDescription())
                            .recipients(Set.of(k.getEmail()))
                            .body(v.get(0).getAction())
                    .build());
        });

        return pendenciesByUser.get(userService.getAuthenticatedUser()).size();
    }
}
