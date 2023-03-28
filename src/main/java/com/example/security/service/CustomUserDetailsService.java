package com.example.security.service;

import com.example.security.domain.Role;
import com.example.security.domain.UserEntity;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;
    private final LoginAttemptService loginAttemptService;

    public UserEntity findUser(final String userName) {
        return userRepo.findByUsername(userName);
    }

    public void save(final UserEntity user) {
        userRepo.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        login = login.toLowerCase();
        final UserEntity user = findUser(login);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + login + "' could not be found.");
        } else {
            if (loginAttemptService.isBlocked(login)) {
                throw new LockedException("User is blocked");
            }
        }

        final Set<Role> roles = Objects.requireNonNull(user).getRoles();

        final Collection<GrantedAuthority> authorities = new ArrayList<>(roles);

        return new User(login,
                user.getPassword(), user.getActive(), true, true, true,
                authorities
        );
    }
}
