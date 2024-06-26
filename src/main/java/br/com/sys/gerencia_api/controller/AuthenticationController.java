package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.infra.security.DataTokenJWT;
import br.com.sys.gerencia_api.infra.security.JWTService;
import br.com.sys.gerencia_api.model.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid UserEntity dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var tokenJWT = jwtService.gerarToken((UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
    }
}
