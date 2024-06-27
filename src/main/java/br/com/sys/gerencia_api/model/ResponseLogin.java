package br.com.sys.gerencia_api.model;

public record ResponseLogin(String accessToken, Long expiresIn) {
}
