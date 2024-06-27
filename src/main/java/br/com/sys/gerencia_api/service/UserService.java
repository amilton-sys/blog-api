package br.com.sys.gerencia_api.service;

import br.com.sys.gerencia_api.model.RequestCreateUser;
import br.com.sys.gerencia_api.model.ResponseCreateUser;
import br.com.sys.gerencia_api.model.Role;
import br.com.sys.gerencia_api.model.User;
import br.com.sys.gerencia_api.repository.RoleRepository;
import br.com.sys.gerencia_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User newUser(RequestCreateUser dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByEmail(dto.email());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User(dto);
        user.encoderPassword(dto.password(), passwordEncoder);
        user.setRoles(Set.of(basicRole));
        user = userRepository.save(user);

        return user;
    }

    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
