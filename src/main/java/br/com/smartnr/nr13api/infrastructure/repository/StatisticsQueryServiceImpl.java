package br.com.smartnr.nr13api.infrastructure.repository;

import br.com.smartnr.nr13api.api.dto.response.CostForecastResponse;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByPlant;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByResponsible;
import br.com.smartnr.nr13api.domain.model.Device;
import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.service.EquipmentService;
import br.com.smartnr.nr13api.domain.service.StatisticsQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsQueryServiceImpl implements StatisticsQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public List<PendenciesByPlant> countPendenciesByPlant() {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(PendenciesByPlant.class);
        var root = query.from(Pendency.class);

        var plant = root.get("inspection").get("applicableTest").get("id").get("equipment").get("area").get("plant").get("code");

        var selection = builder.construct(PendenciesByPlant.class, plant, builder.count(root.get("id")));
        query.select(selection);
        query.where(builder.equal(root.get("status"), PendencyStatus.STARTED));
        query.groupBy(plant);

        return manager.createQuery(query).getResultList();
    }

    @Override
    public List<PendenciesByResponsible> countPendenciesByResponsible() {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(PendenciesByResponsible.class);
        var root = query.from(Pendency.class);

        var responsible = root.get("responsible").get("name");

        var selection = builder.construct(PendenciesByResponsible.class, responsible, builder.count(root.get("id")));
        query.select(selection);
        query.where(builder.equal(root.get("status"), PendencyStatus.STARTED));
        query.groupBy(responsible);

        return manager.createQuery(query).getResultList();
    }

    public List<CostForecastResponse> costForecast(Long plantId) {

        var equipments = equipmentService.findAllByPlantId(plantId);

        List<CostForecastResponse> forecast = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            var cost = new CostForecastResponse();
            cost.setYear(LocalDate.now().plusMonths(i).getYear());
            cost.setMonth(LocalDate.now().plusMonths(i).getMonth());
            forecast.add(cost);
        }

        equipments.forEach(e -> {
            // Inspeções
            e.getApplicableTests().forEach(at -> {
                at.getNextTestDatesUntil(LocalDate.now().plusMonths(12)).forEach(date -> {
                    forecast.forEach(f -> {
                        if (f.getMonth().equals(date.getMonth()) && f.getYear() == date.getYear()) {
                            f.setInspectionCost(f.getInspectionCost().add(at.getLastCost()));
                        }
                    });
                });
            });

            // Calibrações
            List<Device> devices = new ArrayList<>();
            devices.addAll(e.getPressureSafetyValves());
            devices.addAll(e.getPressureIndicators());
            devices.forEach(d -> {
                d.getNextCalibrationDatesUntil(LocalDate.now().plusMonths(12)).forEach(date -> {
                    forecast.forEach(f -> {
                        if (f.getMonth().equals(date.getMonth()) && f.getYear() == date.getYear()) {
                            f.setCalibrationCost(f.getCalibrationCost().add(d.getLastCalibrationCost()));
                        }
                    });
                });
            });
        });

        return forecast;
    }
}
