package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.repository.filters.PressureIndicatorFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

public class PressureIndicatorSpecs {

    public static Specification<PressureIndicator> withFilter(PressureIndicatorFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (PressureIndicator.class.equals(query.getResultType())) {
                root.fetch("plant");
            }

            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(filter.getStatus())) {
                if (filter.getStatus().equals("inactive")) {
                    predicates.add(criteriaBuilder.equal(root.get("active"), Boolean.FALSE));
                } else if (!filter.getStatus().equals("all")) {
                    predicates.add(criteriaBuilder.equal(root.get("active"), Boolean.TRUE));
                }
            } else {
                predicates.add(criteriaBuilder.equal(root.get("active"), Boolean.TRUE));
            }

            if (!ObjectUtils.isEmpty(filter.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }

            if (!ObjectUtils.isEmpty(filter.getTag())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("tag")), "%" + filter.getTag().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getMinGauge())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minGauge").as(Double.class), filter.getMinGauge()));
            }

            if (!ObjectUtils.isEmpty(filter.getMaxGauge())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxGauge").as(Double.class), filter.getMaxGauge()));
            }

            if (!ObjectUtils.isEmpty(filter.getPlantCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("plant").get("code")), "%" + filter.getPlantCode().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
