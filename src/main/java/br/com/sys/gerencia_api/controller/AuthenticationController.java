package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.model.RequestLogin;
import br.com.sys.gerencia_api.model.ResponseLogin;
import br.com.sys.gerencia_api.service.AuthenticationService;
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
    public ResponseEntity<ResponseLogin> signIn(@RequestBody RequestLogin requestLogin) {
        return ResponseEntity.ok(authenticationService.login(requestLogin));
    }
}
