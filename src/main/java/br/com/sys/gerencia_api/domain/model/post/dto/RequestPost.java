package br.com.sys.gerencia_api.domain.model.post.dto;


import jakarta.validation.constraints.NotBlank;

public record RequestPost(
        @NotBlank(message = "O campo de titulo é obrigatório.")
        String title,
        @NotBlank(message = "O campo de descrição é obrigatório.")
        String description,
        @NotBlank(message = "O campo de conteúdo é obrigatório.")
        String content,
        String thumb
        ) {
}
