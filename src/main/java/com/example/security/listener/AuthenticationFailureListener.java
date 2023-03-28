package com.example.security.listener;

import com.example.security.repository.UserRepository;
import com.example.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private LoginAttemptService loginAttemptService;
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {
        final Object principal = e.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            final String username = (String) e.getAuthentication().getPrincipal();
            if (userRepository.findByUsername(username) != null) {
                loginAttemptService.loginFAiled(username);
            }
        }
    }
}

