package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.model.Plant;
import br.com.smartnr.nr13api.domain.model.User;
import br.com.smartnr.nr13api.domain.repository.PlantRepository;
import br.com.smartnr.nr13api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {

    private final PlantRepository plantRepository;
    private final UserService userService;

    @Transactional
    public Plant create(Plant plant) {
        log.info("Iniciando processo de cadastro de Planta obj={}", plant);
        var user = userService.findById(10000L);
        plant.setUpdatedBy(user);
        plant = plantRepository.save(plant);
        log.info("Cadastro de Planta realizado com sucesso id={}", plant.getId());
        return plant;
    }

}
