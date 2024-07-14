package br.com.sys.gerencia_api.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private final JavaMailSender emailSender;

    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email.para());
            message.setSubject(email.assunto());
            message.setText(email.mensagem());
            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail" + e.getMessage());
        }
    }


    public void sendEmail(String email, String username, int code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("Redefinição de senha");
            message.setText("Seu código: " + code);
            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail" + e.getMessage());
        }
    }

}
