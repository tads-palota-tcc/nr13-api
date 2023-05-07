package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.domain.exception.BusinessException;
import br.com.smartnr.nr13api.domain.exception.DeviceNotFoundException;
import br.com.smartnr.nr13api.domain.model.PressureIndicator;
import br.com.smartnr.nr13api.domain.repository.PressureIndicatorRepository;
import br.com.smartnr.nr13api.domain.repository.filters.PressureIndicatorFilter;
import br.com.smartnr.nr13api.domain.repository.specs.PressureIndicatorSpecs;
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
public class PressureIndicatorService {

    private final PressureIndicatorRepository piRepository;
    private final PlantService plantService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public PressureIndicator create(PressureIndicator entity) {
        log.info("Iniciando processo de cadastro de Indicador de Pressão tag={}", entity.getTag());
        entity.setUpdatedBy(userService.getAuthenticatedUser());
        entity = piRepository.save(entity);
        log.info("Cadastro de Indicador de Pressão realizado com sucesso id={}", entity.getId());
        return entity;
    }

    @Transactional
    public PressureIndicator update(Long id, PressureIndicator entity) {
        log.info("Iniciando processo de atualização de Indicador de Pressão tag={}", entity.getTag());
        var existing = this.findOrFail(id);
        modelMapper.map(entity, existing);
        existing.setUpdatedBy(userService.getAuthenticatedUser());
        piRepository.save(existing);
        return existing;
    }

    @Transactional
    public void inactivate(Long id) {
        log.info("Iniciando processo de inativação de Indicador de Pressão Id={}", id);
        var entity = this.findOrFail(id);
        if (!entity.getActive()) {
            throw new BusinessException(String.format("Indicador de Pressão com id=%d já encontra-se inativo", id));
        }
        entity.setActive(Boolean.FALSE);
    }

    @Transactional
    public void activate(Long id) {
        log.info("Iniciando processo de ativação de Indicador de Pressão Id={}", id);
        var entity = this.findOrFail(id);
        if (entity.getActive()) {
            throw new BusinessException(String.format("Indicador de Pressão com id=%d já encontra-se ativo", id));
        }
        entity.setActive(Boolean.TRUE);
    }

    public Page<PressureIndicator> findByFilter(PressureIndicatorFilter filter, Pageable pageable) {
        log.info("Iniciando processo de listagem de Indicador de Pressão por filtro={}", filter);
        return piRepository.findAll(PressureIndicatorSpecs.withFilter(filter), pageable);
    }

    public List<PressureIndicator> findAllAvailableByUserPlant() {
        log.info("Iniciando processo de listagem de Indicadores de Pressão disponíveis");
        return piRepository.findAvailableByPlants(plantService.findByUser());
    }

    public PressureIndicator findByTagAndPlantCode(String tag, String plantCode) {
        log.info("Iniciando consulta de Indicadores de Pressão por Tag={} e Planta={}", tag, plantCode);
        return piRepository.findByTagAndPlantCode(tag, plantCode)
                .orElseThrow(() -> new DeviceNotFoundException(String.format("Não foi encontrado um dispositivo do tipo PI com Tag=%s para a planta de código=%s", tag, plantCode)));
    }

    public PressureIndicator findById(Long id) {
        log.info("Iniciando busca de Indicador de Pressão por id={}", id);
        return findOrFail(id);
    }

    private PressureIndicator findOrFail(Long id) {
        return piRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }

}
