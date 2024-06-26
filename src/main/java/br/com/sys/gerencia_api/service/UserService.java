package br.com.sys.gerencia_api.service;

import br.com.sys.gerencia_api.model.UserEntity;
import br.com.sys.gerencia_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository usuarioRepository;
    private PasswordEncoder encoder;

    public void createUser(UserEntity user) {
        String pass = user.getPassword();
        user.setSenha(encoder.encode(pass));
        usuarioRepository.save(user);
    }
}
