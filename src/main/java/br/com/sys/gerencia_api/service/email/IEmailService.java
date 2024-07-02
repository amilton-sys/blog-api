package br.com.sys.gerencia_api.service.email;

public interface IEmailService {
    void sendEmail(Email email);

    void sendEmail(String email, String username, int code);
}
