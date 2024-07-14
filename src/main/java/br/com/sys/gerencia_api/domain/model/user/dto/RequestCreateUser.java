package br.com.sys.gerencia_api.domain.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCreateUser(
        @NotBlank(message = "O campo de username é obrigatório.")
        String username,
        @NotBlank(message = "O campo de e-mail é obrigatório.")
        @Email(message = "Preencha o campo de e-mail corretamente.")
        String email,
        @NotBlank(message = "O campo de senha é obrigatório.")
        @Size(min = 6, max = 20, message = "Preencha com no mínimo 6 caracteres e no máximo 20.")
        String password) {
}
