package com.nerdysoft.mapper.user;

import com.nerdysoft.mapper.GeneralMapper;
import com.nerdysoft.model.User;
import com.nerdysoft.model.UserRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper implements GeneralMapper<User, UserRegistrationDto> {

    @Override
    public UserRegistrationDto convertToDto(User model) {
        return UserRegistrationDto.builder()
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .build();
    }

    @Override
    public User convertToModel(UserRegistrationDto dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
