package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.Pendency;
import br.com.smartnr.nr13api.domain.repository.filters.PendencyFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class PendencySpecs {

    public static Specification<Pendency> withFilter(PendencyFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (Pendency.class.equals(query.getResultType())) {
                root.fetch("inspection");
            }

            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(filter.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }

            if (!ObjectUtils.isEmpty(filter.getAuthorName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("author").get("name")), "%" + filter.getAuthorName().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getResponsibleName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("responsible").get("name")), "%" + filter.getResponsibleName().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getOpenedAt())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("openedAt").as(LocalDate.class), filter.getOpenedAt()));
            }

            if (!ObjectUtils.isEmpty(filter.getDeadLine())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deadLine").as(LocalDate.class), filter.getDeadLine()));
            }

            if (!ObjectUtils.isEmpty(filter.getPlantCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(
                        root.get("inspection")
                                .get("applicableTest")
                                .get("id")
                                .get("equipment")
                                .get("area")
                                .get("plant")
                                .get("code")), "%" + filter.getPlantCode().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
