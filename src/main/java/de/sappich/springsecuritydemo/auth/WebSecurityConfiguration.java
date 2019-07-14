package de.sappich.springsecuritydemo.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomerAuthProvider customAuthProvider;

    public WebSecurityConfiguration(CustomerAuthProvider customAuthProvider) {
        this.customAuthProvider = customAuthProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();

        // all requests must be authenticated
        http.authorizeRequests()
                .mvcMatchers("/admin")
                .hasAuthority("ADMIN")
                .anyRequest().authenticated();
    }

    // exclude the register endpoint from security
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/register", "/h2-console");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }
}

@Component
class CustomerAuthProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    CustomerAuthProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        if (userDetailsService.matches(username, password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.singletonList(new SimpleGrantedAuthority("USER")));
        }
        throw new BadCredentialsException("username or password invalid");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
