package br.com.smartnr.nr13api.api.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {

    private String id;
    private String name;
    private Set<String> roles;

}
