package de.sappich.springsecuritydemo.auth;

import de.sappich.springsecuritydemo.user.CustomUser;
import de.sappich.springsecuritydemo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        final Optional<CustomUser> userOpt = repository.findByUsername(username);

        final CustomUser user = userOpt.get();
        final Set<SimpleGrantedAuthority> authorities = user.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                // TODO add dedicated properties in CustomUser for the following values
                .credentialsExpired(!user.isEnabled())
                .accountExpired(!user.isEnabled())
                .build();
    }

    public @NotNull @NotBlank String saveUser(CustomUser customUser) {
        // encode the password
        customUser.addAuthority("USER");
        customUser.setPassword(passwordEncoder().encode(customUser.getPassword()));
        final CustomUser savedCustomUser = this.repository.save(customUser);
        return savedCustomUser.getUsername();
    }

    public boolean matches(String username, String password) {
        final Optional<CustomUser> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        } else {
            @NotNull @NotBlank final String userPassword = user.get().getPassword();
            return passwordEncoder().matches(password, userPassword);
        }
    }

    public String saveUserAdmin(CustomUser customUser) {
        customUser.addAuthority("ADMIN");
        return saveUser(customUser);
    }

}
