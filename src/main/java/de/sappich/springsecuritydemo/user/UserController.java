package de.sappich.springsecuritydemo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("me")
    public ResponseEntity<CustomUser> userInfos(Principal principal) {
        final String username = principal.getName();
        final Optional<CustomUser> user = repository.findByUsername(username);
        return ResponseEntity.ok(user.get());
    }
}
