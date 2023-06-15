package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.dto.response.CostForecastResponse;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByPlant;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByResponsible;
import br.com.smartnr.nr13api.infrastructure.repository.StatisticsQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsQueryServiceImpl statisticsQueryService;

    @GetMapping("plants-pendencies")
    public ResponseEntity<List<PendenciesByPlant>> pendenciesByPlants() {
        return ResponseEntity.ok(statisticsQueryService.countPendenciesByPlant());
    }

    @GetMapping("responsibles-pendencies")
    public ResponseEntity<List<PendenciesByResponsible>> pendenciesByResponsible() {
        return ResponseEntity.ok(statisticsQueryService.countPendenciesByResponsible());
    }

    @GetMapping("costs-forecast")
    public List<CostForecastResponse> costForecast(@RequestParam Long plantId) {
        return statisticsQueryService.costForecast(plantId);
    }
}
