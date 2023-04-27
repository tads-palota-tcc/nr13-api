package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.repository.filters.CalibrationFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalibrationSpecs {

    public static Specification<PressureIndicator> withFilter(CalibrationFilter filter) {
        return (root, query, criteriaBuilder) -> {

            if (Calibration.class.equals(query.getResultType())) {
                root.fetch("device");
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

            if (!ObjectUtils.isEmpty(filter.getReportNumber())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("reportNumber")), "%" + filter.getReportNumber().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getCalibrationStatus())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("calibrationStatus")), "%" + filter.getCalibrationStatus().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getDeviceTag())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("getDeviceTag")), "%" + filter.getDeviceTag().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getExecutionDate())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("executionDate").as(LocalDate.class), filter.getExecutionDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
