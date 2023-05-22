package br.com.smartnr.nr13api.domain.service;

import br.com.smartnr.nr13api.api.dto.response.PendenciesByPlant;
import br.com.smartnr.nr13api.api.dto.response.PendenciesByResponsible;

import java.util.List;

public interface StatisticsQueryService {

    List<PendenciesByPlant> countPendenciesByPlant();

    List<PendenciesByResponsible> countPendenciesByResponsible();
}
