package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("pendencies")
    @Secured({"NOTIFICATION"})
    public void notifyOverduePendencies() {
        notificationService.notifyPendencies();
    }

    @GetMapping("calibrations")
    @Secured({"NOTIFICATION"})
    public void notifyOverdueCalibrations() {
        notificationService.notifyCalibrations();
    }

    @GetMapping("inspections")
    @Secured({"NOTIFICATION"})
    public void notifyOverdueInspections() {
        notificationService.notifyInspections();
    }
}
