package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.DeviceNotFoundException;
import br.com.smartnr.nr13api.domain.model.PressureSafetyValve;
import br.com.smartnr.nr13api.domain.repository.PressureSafetyValveRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PressureSafetyValveFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PressureSafetyValveSpecs;
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
public class PressureSafetyValveService {

    private final PressureSafetyValveRepository psvRepository;
    private final PlantService plantService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public PressureSafetyValve create(PressureSafetyValve entity) {
        log.info("Iniciando processo de cadastro de Válvula de Segurança tag={}", entity.getTag());
        entity.setUpdatedBy(userService.getAuthenticatedUser());
        entity = psvRepository.save(entity);
        log.info("Cadastro de Válvula de Segurança realizado com sucesso id={}", entity.getId());
        return entity;
    }

    @Transactional
    public PressureSafetyValve update(Long id, PressureSafetyValve entity) {
        log.info("Iniciando processo de atualização de Válvula de Segurança tag={}", entity.getTag());
        var existing = this.findOrFail(id);
        modelMapper.map(entity, existing);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        psvRepository.save(existing);
        return existing;
    }

    public Page<PressureSafetyValve> findByFilter(PressureSafetyValveFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Válvula de Segurança por filtro={}", filter);
        return psvRepository.findAll(PressureSafetyValveSpecs.withFilter(filter), pageable);
    }

    public List<PressureSafetyValve> findAllAvailableByUserPlant() {
        log.info("Iniciando processo de listagem de Válvulas de Segurança disponíveis");
        return psvRepository.findAvailableByPlants(plantService.findByUser());
    }

    public PressureSafetyValve findById(Long id) {
        log.info("Iniciando processo de consulta de Válvulas de Segurança por id: {}", id);
        return findOrFail(id);
    }

    private PressureSafetyValve findOrFail(Long id) {
        return psvRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }

}