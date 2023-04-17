package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.Area;
import br.com.smartnr.nr13api.domain.model.Equipment;
import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.repository.filters.AreaFilter;
import br.com.smartnr.nr13api.domain.repository.filters.EquipmentFilter;
import br.com.smartnr.nr13api.domain.repository.filters.PlantFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

public class EquipmentSpecs {

    public static Specification<Equipment> withFilter(EquipmentFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (Equipment.class.equals(query.getResultType())) {
                root.fetch("area");
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

            if (!ObjectUtils.isEmpty(filter.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + filter.getName().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getAreaCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("area").get("code")), "%" + filter.getAreaCode().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
