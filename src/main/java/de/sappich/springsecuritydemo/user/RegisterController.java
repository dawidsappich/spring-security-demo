package de.sappich.springsecuritydemo.user;

import de.sappich.springsecuritydemo.auth.CustomUserDetailsService;
import de.sappich.springsecuritydemo.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final CustomUserDetailsService userDetailsService;

    public RegisterController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        final User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        final User savedUser = this.userDetailsService.saveUser(user);
        return ResponseEntity.ok(new ResponseMessage(ResponseMessage.Status.SUCCESS, "registered for user: " + savedUser.getUsername()));
    }
}
