package br.com.sys.gerencia_api.domain.model.user.dto;


import br.com.sys.gerencia_api.domain.model.user.User;

import java.util.UUID;

public record ResponseUserDetail(
        UUID id,
        String username,
        String email
) {
    public ResponseUserDetail(User user) {
        this(user.getUserId(),user.getUsername(), user.getEmail());
    }
}
