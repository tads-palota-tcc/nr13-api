package br.com.smartnr.nr13api.infrastructure.repository;

import br.com.smartnr.nr13api.api.dto.response.PendenciesByPlant;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByResponsible;
import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.model.PendencyStatus;
import br.com.smartnr.nr13api.domain.service.StatisticsQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticsQueryServiceImpl implements StatisticsQueryService {

    @PersistenceContext
    private EntityManager manager;

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
}
