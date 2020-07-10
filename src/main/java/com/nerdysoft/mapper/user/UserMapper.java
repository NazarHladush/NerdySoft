package com.nerdysoft.mapper.user;

import com.nerdysoft.dto.UserDto;
import com.nerdysoft.mapper.GeneralMapper;
import com.nerdysoft.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements GeneralMapper<User, UserDto> {
    @Override
    public UserDto convertToDto(User model) {
        return UserDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .roles(model.getRoles())
                .build();
    }

    @Override
    public User convertToModel(UserDto dto) {
        return null;
    }
}
