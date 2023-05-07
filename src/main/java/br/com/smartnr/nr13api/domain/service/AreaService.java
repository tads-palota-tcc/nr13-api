package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.AreaNotFoundException;
import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.model.Area;
import br.com.smartnr.nr13api.domain.repository.AreaRepository;
import br.com.smartnr.nr13api.domain.repository.filters.AreaFilter;
import br.com.smartnr.nr13api.domain.repository.specs.AreaSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AreaService {

    private final AreaRepository areaRepository;
    private final PlantService plantService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public Area create(Area area) {
        log.info("Iniciando processo de cadastro de Área: {}", area.getCode());
        area.setUpdatedBy(userService.getAuthenticatedUser());
        area = areaRepository.save(area);
        log.info("Cadastro de Área realizado com sucesso id={}", area.getId());
        return area;
    }

    @Transactional
    public Area update(Long id, Area area) {
        log.info("Iniciando processo de atualização de Área: {}", area.getCode());
        var existing = this.findOrFail(id);
        modelMapper.map(area, existing);
        var plant = plantService.findById(area.getPlant().getId());
        area.setPlant(plant);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        areaRepository.save(existing);
        return existing;
    }

    public Page<Area> findByFilter(AreaFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Área filtro={}", filter);
        return areaRepository.findAll(AreaSpecs.withFilter(filter), pageable);
    }

    public Area findById(Long id) {
        log.info("Iniciando busca de Área id={}", id);
        return findOrFail(id);
    }

    public List<Area> findTop10(String code) {
        log.info("Iniciando processo de listagem das 10 primeiras Áreas ativas com código={}", code);
        return areaRepository.findTop10ByCodeContainingIgnoreCaseOrderByCode(code);
    }

    @Transactional
    public void inactivate(Long id) {
        log.info("Iniciando processo de inativação de Área Id={}", id);
        var entity = this.findOrFail(id);
        if (!entity.getActive()) {
            throw new BusinessException(String.format("Área com id=%d já encontra-se inativa", id));
        }
        entity.setActive(Boolean.FALSE);
    }

    @Transactional
    public void activate(Long id) {
        log.info("Iniciando processo de ativação de Área Id={}", id);
        var entity = this.findOrFail(id);
        if (entity.getActive()) {
            throw new BusinessException(String.format("Área com id=%d já encontra-se ativa", id));
        }
        entity.setActive(Boolean.TRUE);
    }

    private Area findOrFail(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new AreaNotFoundException(id));
    }

}
