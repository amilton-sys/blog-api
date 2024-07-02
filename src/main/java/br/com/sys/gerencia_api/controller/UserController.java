package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.domain.model.code.Code;
import br.com.sys.gerencia_api.domain.model.user.dto.RequestCreateUser;
import br.com.sys.gerencia_api.domain.model.user.dto.ResponseCreateUser;
import br.com.sys.gerencia_api.domain.model.user.dto.ResponseUserDetail;
import br.com.sys.gerencia_api.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<ResponseCreateUser> newUser(@RequestBody RequestCreateUser dto, UriComponentsBuilder uriBuilder) {
        var user = userService.newUser(dto);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getUserId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseCreateUser(user));
    }


    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Page<ResponseUserDetail>> listUsers(@PageableDefault(
            sort = {"username"}) Pageable pageable) {
        var users = userService.listUsers(pageable)
                .map(ResponseUserDetail::new);
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/sendPin")
    public ResponseEntity<Void> sendPin(@RequestParam String email) {
        userService.sendCodeByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/forgoutPassword")
    public ResponseEntity<Void> forgoutPassword(@RequestParam int code, @RequestParam String password) {
        userService.updatePassword(code, password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/codes")
    public ResponseEntity<List<Code>> getAllCodes(){
        var codes = userService.findAllCodes();
        return ResponseEntity.ok(codes);
    }
}
