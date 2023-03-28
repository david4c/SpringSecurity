package com.example.security.config;


import com.example.security.handler.CustomAuthenticationFailureHandler;
import com.example.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String PASSWORD_PREFIX = "bcrypt";
    private static int HASH_STRENGTH = 10;

    private final CustomUserDetailsService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http,
            CustomAuthenticationFailureHandler authenticationFailureHandler
    ) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/", "/info","/login*").permitAll()
                        .requestMatchers("/secret/**").hasRole("VIEW_INFO")
                        .requestMatchers(HttpMethod.GET, "/about").hasRole("VIEW_INFO")
                        .requestMatchers(HttpMethod.GET, "/admin","/blocked").hasRole("VIEW_ADMIN")
                        .anyRequest().authenticated()
                )
//                        .formLogin(Customizer.withDefaults())
//                        .logout(Customizer.withDefaults());
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
        http.authenticationProvider(authenticationProvider());

        return http.csrf().disable().build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(PASSWORD_PREFIX, encoders);
    }
}
