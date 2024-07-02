package br.com.sys.gerencia_api.domain.model.post.dto;


import jakarta.validation.constraints.NotBlank;

public record RequestPost(
        @NotBlank(message = "O campo de conteúdo é obrigatório.")
        String content) {
}
