package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.InspectionNotFoundException;
import br.com.smartnr.nr13api.domain.model.Inspection;
import br.com.smartnr.nr13api.domain.repository.InspectionRepository;
import br.com.smartnr.nr13api.domain.repository.filters.InspectionFilter;
import br.com.smartnr.nr13api.domain.repository.specs.InspectionSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InspectionService {

    private final InspectionRepository inspectionRepository;

    public Page<Inspection> findByFilter(InspectionFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Calibração filtro={}", filter);
        var page = inspectionRepository.findAll(InspectionSpecs.withFilter(filter, null), pageable);
        return page;
    }

    private Inspection findOrFail(Long id) {
        return inspectionRepository.findById(id)
                .orElseThrow(() -> new InspectionNotFoundException(id));
    }
}
