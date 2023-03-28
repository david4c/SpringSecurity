package com.example.security.converter;

import com.example.security.config.dto.UserDto;
import com.example.security.domain.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDto implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(UserEntity source) {
        return UserDto.builder()
                      .id(source.getId())
                      .username(source.getUsername())
                      .roles(source.getRoles())
                      .build();
    }
}
