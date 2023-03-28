package com.example.security.service;

import com.example.security.config.dto.UserDto;
import com.example.security.converter.UserToDto;
import com.example.security.domain.UserEntity;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final LoginAttemptService loginAttemptService;
    private final UserRepository userRepository;
    private final UserToDto userToDtoConverter;

    public String getInfo() {
        final String[] stats = {"MVC application", "RESTful API", "database connection", "server uptime"};
        final Random random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        final int index = random.nextInt(stats.length);
        return "Random stat: " + stats[index];
    }

    public String getAbout() {
        return "About this Site";
    }

    public String getAdmin() {
        return "Admin page ....";
    }

    public List<UserDto> getBLockedUsers(){
        final List<String> userList = loginAttemptService.getAttemptedUsers();

        return userRepository.findByUsernameIn(userList).stream()
                .map(userToDtoConverter::convert).collect(Collectors.toList());
    }
}
