package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.PlantNotFoundException;
import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.repository.PlantRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PlantFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PlantSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {

    private final PlantRepository plantRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public Plant create(Plant plant) {
        log.info("Iniciando processo de cadastro de Planta: {}", plant.getCode());
        plant.setUpdatedBy(userService.getAuthenticatedUser());
        plant = plantRepository.save(plant);
        log.info("Cadastro de Planta realizado com sucesso id={}", plant.getId());
        return plant;
    }

    @Transactional
    public Plant update(Long id, Plant plant) {
        log.info("Iniciando processo de atualização de Planta: {}", plant.getCode());
        var existing = this.findOrFail(id);
        modelMapper.map(plant, existing);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        plantRepository.save(existing);
        return existing;
    }

    public Page<Plant> findByFilter(PlantFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Planta filtro={}", filter);
        return plantRepository.findAll(PlantSpecs.withFilter(filter), pageable);
    }

    public Plant findById(Long id) {
        log.info("Iniciando busca de Planta id={}", id);
        return findOrFail(id);
    }

    public List<Plant> findByUser() {
        var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Iniciando processo de listagem de Planta por usuário Id={}", user.getUsername());
        return userService.getAuthenticatedUser().getPlants()
                .stream().sorted().toList();
    }

    public List<Plant> findTop10(String code) {
        log.info("Iniciando processo de listagem das 10 primeiras Plantas ativas com código={}", code);
        return plantRepository.findTop10ByCodeContainingIgnoreCaseOrderByCode(code);
    }

    @Transactional
    public void inactivate(Long id) {
        log.info("Iniciando processo de inativação de Planta Id={}", id);
        var entity = this.findOrFail(id);
        if (!entity.getActive()) {
            throw new BusinessException(String.format("Planta com id=%d já encontra-se inativa", id));
        }
        entity.setActive(Boolean.FALSE);
    }

    @Transactional
    public void activate(Long id) {
        log.info("Iniciando processo de ativação de Planta Id={}", id);
        var entity = this.findOrFail(id);
        if (entity.getActive()) {
            throw new BusinessException(String.format("Planta com id=%d já encontra-se ativa", id));
        }
        entity.setActive(Boolean.TRUE);
    }

    private Plant findOrFail(Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException(id));
    }

}
