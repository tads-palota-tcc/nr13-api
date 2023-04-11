package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.User;
import br.com.smartnr.nr13api.domain.repository.PlantRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PlantFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PlantSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {

    private final PlantRepository plantRepository;

    @Transactional
    public Plant create(Plant plant) {
        log.info("Iniciando processo de cadastro de Planta obj={}", plant);
        var user = new User();
        user.setId(100000L);
        plant.setUpdatedBy(user);
        plant = plantRepository.save(plant);
        log.info("Cadastro de Planta realizado com sucesso id={}", plant.getId());
        return plant;
    }

    public Page<Plant> findByFilter(PlantFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Planta filtro={}", filter);
        return plantRepository.findAll(PlantSpecs.withFilter(filter), pageable);
    }

}
