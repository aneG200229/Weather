package org.aneg.mapper;

import org.aneg.dto.user.UserRegisterDto;
import org.aneg.dto.user.UserResponseDto;
import org.aneg.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserRegisterDto dto) {
        User user = new User();
        user.setLogin(dto.getUsername());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setLogin(user.getLogin());
        return dto;
    }
}
