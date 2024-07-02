package br.com.sys.gerencia_api.service.user;

import br.com.sys.gerencia_api.domain.model.code.Code;
import br.com.sys.gerencia_api.domain.model.user.Role;
import br.com.sys.gerencia_api.domain.model.user.User;
import br.com.sys.gerencia_api.domain.model.user.dto.RequestCreateUser;
import br.com.sys.gerencia_api.domain.model.user.repository.RoleRepository;
import br.com.sys.gerencia_api.domain.model.user.repository.UserRepository;
import br.com.sys.gerencia_api.service.code.CodeService;
import br.com.sys.gerencia_api.service.email.IEmailService;
import br.com.sys.gerencia_api.service.exception.CodeAlreadyInUseException;
import br.com.sys.gerencia_api.service.exception.CodeExpiredException;
import br.com.sys.gerencia_api.service.exception.InvalidCodeException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;
    private final CodeService codeService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, IEmailService emailService, CodeService codeService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.codeService = codeService;
    }

    @Transactional
    public User newUser(RequestCreateUser dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByEmail(dto.email());
        if (userFromDb.isPresent()) {
            throw new EntityExistsException("User with email " + dto.email() + " already exists");
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

    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(EntityNotFoundException::new);
    }

    public void sendCodeByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        var code = codeService.generateCode(email);
        emailService.sendEmail(email, user.getUsername(), code.getCode());
    }

    public void updatePassword(int code, String password) {
        var codeEntity = codeService.findByCode(code).orElseThrow(() -> new CodeExpiredException("Code expired."));
        if (!codeEntity.isActive()){
            throw new CodeAlreadyInUseException("Code already in use");
        }
        var isEqual = Objects.equals(codeEntity.getCode(), code);
        if (!isEqual) {
            throw new InvalidCodeException("Invalid code");
        }
        var user = userRepository.findByEmail(codeEntity.getEmail()).orElseThrow(EntityNotFoundException::new);
        user.setPassword(passwordEncoder.encode(password));
        codeEntity.setActive(false);
        userRepository.save(user);
        codeService.deleteCode(codeEntity);
    }

    public List<Code> findAllCodes(){
        return codeService.findAll();
    }
}
