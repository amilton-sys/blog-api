package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.domain.model.user.dto.RequestLogin;
import br.com.sys.gerencia_api.domain.model.user.dto.ResponseLogin;
import br.com.sys.gerencia_api.service.user.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> signIn(@RequestBody @Valid RequestLogin requestLogin) {
        return ResponseEntity.ok(authenticationService.login(requestLogin));
    }
}
