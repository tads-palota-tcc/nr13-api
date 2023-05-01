package br.com.smartnr.nr13api.domain.repository.specs;

import br.com.smartnr.nr13api.domain.model.Calibration;
import br.com.smartnr.nr13api.domain.repository.filters.CalibrationFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalibrationSpecs {

    public static Specification<Calibration> withFilter(CalibrationFilter filter, Long deviceId) {
        return (root, query, criteriaBuilder) -> {

            if (Calibration.class.equals(query.getResultType())) {
                root.fetch("device");
            }

            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(deviceId)) {
                predicates.add(criteriaBuilder.equal(root.get("device").get("id"), deviceId));
            }

            if (!ObjectUtils.isEmpty(filter.getExecutionDate())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("executionDate").as(LocalDate.class), filter.getExecutionDate()));
            }

            if (!ObjectUtils.isEmpty(filter.getReportNumber())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("reportNumber")), "%" + filter.getReportNumber().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getReportNumber())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("executorCompany")), "%" + filter.getExecutorCompany().toUpperCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.getStatus())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("status")), "%" + filter.getStatus().toUpperCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
