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

    @PostMapping("register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        final CustomUser customUser = createUser(userRegisterDto);
        final String username = this.userDetailsService.saveUser(customUser);
        return ResponseEntity.ok(createResponseMessage(username));
    }

    @PostMapping("register/superuser")
    public ResponseEntity<ResponseMessage> registerAdmin(@RequestBody UserRegisterDto userRegisterDto) {
        final CustomUser customUser = createUser(userRegisterDto);
        final String username = this.userDetailsService.saveUserAdmin(customUser);
        return ResponseEntity.ok(createResponseMessage(username));
    }

    private ResponseMessage createResponseMessage(String username) {
        return new ResponseMessage(ResponseMessage.Status.SUCCESS, "registered for user: " + username);
    }

    public RegisterController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private CustomUser createUser(UserRegisterDto userRegisterDto) {
        final CustomUser customUser = new CustomUser();
        customUser.setUsername(userRegisterDto.getUsername());
        customUser.setPassword(userRegisterDto.getPassword());
        return customUser;
    }
}
