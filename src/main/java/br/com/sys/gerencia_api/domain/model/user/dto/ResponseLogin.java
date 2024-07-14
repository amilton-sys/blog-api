package br.com.sys.gerencia_api.domain.model.user.dto;

public record ResponseLogin(String accessToken, Long expiresIn) {
}
