package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.Area;
import br.com.smartnr.nr13api.domain.repository.filters.AreaFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

public class AreaSpecs {

    public static Specification<Area> withFilter(AreaFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (Area.class.equals(query.getResultType())) {
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

            if (!ObjectUtils.isEmpty(filter.getCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("code")), "%" + filter.getCode().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + filter.getName().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getPlantCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("plant").get("code")), "%" + filter.getPlantCode().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
