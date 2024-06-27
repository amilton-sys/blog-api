package br.com.sys.gerencia_api.model;

public record ResponseCreateUser(String username, String email) {
    public ResponseCreateUser(User user) {
        this(user.getUsername(), user.getEmail());
    }
}
