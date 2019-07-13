package de.sappich.springsecuritydemo.user;

import de.sappich.springsecuritydemo.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(new ResponseMessage(ResponseMessage.Status.SUCCESS, "you are registered"));
    }
}
