package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.model.UserEntity;
import br.com.sys.gerencia_api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService service;
    @PostMapping
    public void postUser(@RequestBody UserEntity user){
        service.createUser(user);
    }
}
