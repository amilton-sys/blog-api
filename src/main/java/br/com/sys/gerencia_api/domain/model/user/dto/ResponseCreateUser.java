package br.com.sys.gerencia_api.domain.model.user.dto;

import br.com.sys.gerencia_api.domain.model.user.User;

public record ResponseCreateUser(String username, String email) {
    public ResponseCreateUser(User user) {
        this(user.getUsername(), user.getEmail());
    }
}
