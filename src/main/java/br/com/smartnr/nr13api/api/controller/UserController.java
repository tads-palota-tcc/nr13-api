package br.com.smartnr.nr13api.api.controller;

import br.com.smartnr.nr13api.api.assembler.UserAssembler;
import br.com.smartnr.nr13api.api.dto.response.UserDetailResponse;
import br.com.smartnr.nr13api.api.dto.response.UserSummaryResponse;
import br.com.smartnr.nr13api.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    public UserDetailResponse findAuthenticatedUser() {
        log.info("Recebendo chamada para consulta de Usuário autenticado");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userService.findById(Long.parseLong(userDetails.getUsername()));
        return userAssembler.toDetailResponse(user);
    }

    @GetMapping(params = {"name"})
    public List<UserSummaryResponse> findTop10(@RequestParam String name) {
        return userAssembler.toSummaryList(userService.findTop10(name));
    }

}
