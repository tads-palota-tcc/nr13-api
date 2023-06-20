package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.*;
import br.com.smartnr.nr13api.domain.repository.ApplicableTestRepository;
import br.com.smartnr.nr13api.domain.repository.DeviceRepository;
import br.com.smartnr.nr13api.domain.repository.PendencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final PendencyRepository pendencyRepository;
    private final ApplicableTestRepository applicableTestRepository;
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final EmailService emailService;

    @Value("${pendency.days.to.expiration.alert}")
    private Long daysToExpirationAlert;

    public void notifyPendencies() {
        Map<User, List<Pendency>> pendenciesByUser = new HashMap<>();

        pendencyRepository.findAllOverdue(LocalDate.now().plusDays(daysToExpirationAlert)).forEach(p -> {
            if (!pendenciesByUser.containsKey(p.getResponsible())) {
                pendenciesByUser.put(p.getResponsible(), new ArrayList<>());
            }
            pendenciesByUser.get(p.getResponsible()).add(p);
        });

        pendenciesByUser.forEach((k, v) -> emailService.send(EmailService.MailMessage.builder()
                .subject(String.format("Pendências NR-13 vencendo nos próximos %d dias", daysToExpirationAlert))
                .recipient(k.getEmail())
                .body("pendencies-overdue.html")
                .variable("name", k.getName())
                .variable("days", daysToExpirationAlert)
                .variable("pendencies", v)
                .build()));
    }

    public void notifyCalibrations() {
        Map<Plant, List<Device>> devicesByPlant = new HashMap<>();

        deviceRepository.findAllOverdue(LocalDate.now().plusDays(daysToExpirationAlert)).forEach(d -> {
            if (!devicesByPlant.containsKey(d.getPlant())) {
                devicesByPlant.put(d.getPlant(), new ArrayList<>());
            }
            devicesByPlant.get(d.getPlant()).add(d);
        });

        devicesByPlant.forEach((k, v) -> {
            var recipients = userService.findAllByPlant(k);
            emailService.send(EmailService.MailMessage.builder()
                    .subject(String.format("Calibrações de %s vencendo nos próximos %d dias", k.getCode(), daysToExpirationAlert))
                    .recipients(recipients.stream().map(User::getEmail).toList())
                    .body("calibrations-overdue.html")
                    .variable("plant", k.getCode())
                    .variable("devices", v)
                    .variable("days", daysToExpirationAlert)
                    .build());
        });

    }

    public void notifyInspections() {
        Map<Plant, List<ApplicableTest>> testsByPlant = new HashMap<>();

        applicableTestRepository.findAllOverdue(LocalDate.now().plusDays(daysToExpirationAlert)).forEach(a -> {
            var plant = a.getId().getEquipment().getArea().getPlant();
            if (!testsByPlant.containsKey(plant)) {
                testsByPlant.put(plant, new ArrayList<>());
            }
            testsByPlant.get(plant).add(a);
        });

        testsByPlant.forEach((k, v) -> {
            var recipients = userService.findAllByPlant(k);
            emailService.send(EmailService.MailMessage.builder()
                    .subject(String.format("Inspeções de %s vencendo nos próximos %d dias", k.getCode(), daysToExpirationAlert))
                    .recipients(recipients.stream().map(User::getEmail).toList())
                    .body("inspections-overdue.html")
                    .variable("plant", k.getCode())
                    .variable("days", daysToExpirationAlert)
                    .variable("tests", v)
                    .build());
        });

    }


}
