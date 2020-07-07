package com.nerdysoft.service;

import com.nerdysoft.exeption.NotFoundException;
import com.nerdysoft.model.User;
import com.nerdysoft.model.UserRegistrationDto;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail (String email) throws NotFoundException;

    boolean registerUser(UserRegistrationDto userRegistrationDto);

    User activateUserByCode(String activationCode);
}
