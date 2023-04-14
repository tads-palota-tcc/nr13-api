package br.com.smartnr.nr13api.api.assembler;

import br.com.smartnr.nr13api.api.dto.response.UserDetailResponse;
import br.com.smartnr.nr13api.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserAssembler {

    private final ModelMapper modelMapper;

    public UserDetailResponse toDetailResponse(User user) {
        Set<String> roles = user.getGroups().stream().flatMap(group -> group.getPermissions().stream())
                .map(permission -> permission.getName().toUpperCase())
                .collect(Collectors.toSet());
        return UserDetailResponse.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .roles(roles)
                .build();
    }

}
