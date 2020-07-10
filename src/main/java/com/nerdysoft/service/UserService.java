package com.nerdysoft.service;

import com.nerdysoft.dto.UserRegistrationDto;
import com.nerdysoft.exeption.ActivatedException;
import com.nerdysoft.exeption.NotFoundException;
import com.nerdysoft.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail (String email) throws NotFoundException;

    boolean registerUser(UserRegistrationDto userRegistrationDto);

    User activateUserByCode(String activationCode) throws ActivatedException;

    String getEmailFromAuthentication();
}
