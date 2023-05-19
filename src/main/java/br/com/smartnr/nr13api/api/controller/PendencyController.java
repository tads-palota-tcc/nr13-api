package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.PendencyAssembler;
import br.com.smartnr.nr13api.api.dto.request.PendencyCreationRequest;
import br.com.smartnr.nr13api.api.dto.response.PendencyDetailResponse;
import br.com.smartnr.nr13api.domain.repository.filters.PendencyFilter;
import br.com.smartnr.nr13api.domain.service.PendencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pendencies")
@Slf4j
@RequiredArgsConstructor
public class PendencyController {

    private final PendencyService pendencyService;
    private final PendencyAssembler pendencyAssembler;

    @PostMapping
    public ResponseEntity<PendencyDetailResponse> create(@RequestBody @Valid PendencyCreationRequest request, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Recebendo chamada para cadastro de Pendência");
        var entity = pendencyService.create(pendencyAssembler.toEntity(request));
        URI uri = uriComponentsBuilder.path("/pendencies/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(pendencyAssembler.toDetailResponse(entity));
    }

    @GetMapping
    public Page<PendencyDetailResponse> findByFilter(PendencyFilter filter, Pageable pageable) {
        log.info("Recebendo chamada para listagem de Pendências");
        return pendencyService.findByFilter(filter, pageable);
    }
}
