package br.com.sys.gerencia_api.service.user;

import br.com.sys.gerencia_api.domain.model.user.dto.RequestLogin;
import br.com.sys.gerencia_api.domain.model.user.dto.ResponseLogin;
import br.com.sys.gerencia_api.domain.model.user.Role;
import br.com.sys.gerencia_api.domain.model.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String ISSUER = "Gerencia API";
    private static final String CLAIM_NAME = "scope";

    public AuthenticationService(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseLogin login(@RequestBody @Valid RequestLogin requestLogin) {
        var user = userRepository.findByEmail(requestLogin.email());
        if (user.isEmpty() || !user.get().isLoginCorret(requestLogin, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(user.get().getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim(CLAIM_NAME, scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new ResponseLogin(jwtValue, expiresIn);
    }
}
