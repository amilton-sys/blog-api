package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.model.RequestCreateUser;
import br.com.sys.gerencia_api.model.ResponseCreateUser;
import br.com.sys.gerencia_api.model.Role;
import br.com.sys.gerencia_api.model.User;
import br.com.sys.gerencia_api.repository.RoleRepository;
import br.com.sys.gerencia_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final UserRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository usuarioRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<ResponseCreateUser> newUser(@RequestBody RequestCreateUser dto, UriComponentsBuilder uriBuilder) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByEmail(dto.email());
        if (userFromDb.isPresent()) {
           throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User(dto);
        user.encoderPassword(dto.password(),passwordEncoder);
        user.setRoles(Set.of(basicRole));

        user = userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getUserId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseCreateUser(user));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}
