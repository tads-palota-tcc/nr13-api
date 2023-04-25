package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import br.com.smartnr.nr13api.domain.repository.filters.PressureSafetyValveFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

public class PressureSafetyValveSpecs {

    public static Specification<PressureSafetyValve> withFilter(PressureSafetyValveFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (PressureSafetyValve.class.equals(query.getResultType())) {
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

            if (!ObjectUtils.isEmpty(filter.getBodySize())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("bodySize")), "%" + filter.getBodySize().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getOpeningPressure())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("openingPressure").as(Double.class), filter.getOpeningPressure()));
            }

            if (!ObjectUtils.isEmpty(filter.getClosingPressure())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("closingPressure").as(Double.class), filter.getClosingPressure()));
            }

            if (!ObjectUtils.isEmpty(filter.getPlantCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("plant").get("code")), "%" + filter.getPlantCode().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
