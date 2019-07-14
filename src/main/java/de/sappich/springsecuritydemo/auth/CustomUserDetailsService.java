package de.sappich.springsecuritydemo.auth;

import de.sappich.springsecuritydemo.user.User;
import de.sappich.springsecuritydemo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public @NotNull @NotBlank String saveUser(User user) {
        // encode the password
        user.addAuthority("USER");
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        final User savedUser = this.repository.save(user);
        return savedUser.getUsername();
    }

    public boolean matches(String username, String password) {
        final Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        } else {
            @NotNull @NotBlank final String userPassword = user.get().getPassword();
            return passwordEncoder().matches(password, userPassword);
        }
    }

    public String saveUserAdmin(User user) {
        user.addAuthority("ADMIN");
        return saveUser(user);
    }

}
