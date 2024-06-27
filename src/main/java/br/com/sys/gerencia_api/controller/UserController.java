package br.com.sys.gerencia_api.controller;

import br.com.sys.gerencia_api.model.RequestCreateUser;
import br.com.sys.gerencia_api.model.ResponseCreateUser;
import br.com.sys.gerencia_api.model.ResponseUserDetail;
import br.com.sys.gerencia_api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


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
}
