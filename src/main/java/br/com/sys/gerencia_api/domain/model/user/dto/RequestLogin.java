package br.com.sys.gerencia_api.domain.model.user.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestLogin(
        @NotBlank(message = "O E-mail é obrigatório.")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        String password) {
}
