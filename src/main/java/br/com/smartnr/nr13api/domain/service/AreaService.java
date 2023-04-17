package br.com.smartnr.nr13api.domain.service;

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
        var plant = plantService.findById(area.getPlant().getId());
        area.setPlant(plant);
        modelMapper.map(area, existing);
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

    private Area findOrFail(Long id) {
        return areaRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

}
