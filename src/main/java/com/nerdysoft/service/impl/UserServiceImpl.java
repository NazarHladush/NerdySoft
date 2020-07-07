package com.nerdysoft.service.impl;

import com.nerdysoft.constant.ErrorMessage;
import com.nerdysoft.exeption.InvalidEmailException;
import com.nerdysoft.exeption.NotFoundException;
import com.nerdysoft.mapper.user.UserRegistrationMapper;
import com.nerdysoft.model.*;
import com.nerdysoft.repository.RoleRepository;
import com.nerdysoft.repository.UserActivationRequestRepository;
import com.nerdysoft.repository.UserRepository;
import com.nerdysoft.service.MailSender;
import com.nerdysoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRegistrationMapper userRegistrationMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserActivationRequestRepository userActivationRequestRepository;
    private final MailSender mailSender;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserRegistrationMapper userRegistrationMapper,
                           PasswordEncoder passwordEncoder,
                           UserActivationRequestRepository userActivationRequestRepository,
                           MailSender mailSender, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRegistrationMapper = userRegistrationMapper;
        this.passwordEncoder = passwordEncoder;
        this.userActivationRequestRepository = userActivationRequestRepository;
        this.mailSender = mailSender;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws NotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    @Transactional
    public boolean registerUser(UserRegistrationDto userRegistrationDto) {
        User user = userRegistrationMapper.convertToModel(userRegistrationDto);
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findByRole("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NOT_ACTIVATED);
        user = userRepository.save(user);

        UserActivationRequest userActivationRequest = new UserActivationRequest(user.getId());
        userActivationRequestRepository.save(userActivationRequest);

        String message = "Welcome. To activate your account follow link:  "
                + "http://localhost:8080/registration/activationUser?activationCode="
                + userActivationRequest.getActivationCode();

        mailSender.send(user.getEmail(), "Activation Code", message);
        return true;
    }

    @Override
    public User activateUserByCode(String activationCode) {
        UserActivationRequest userActivationRequest = userActivationRequestRepository.findByActivationCode(activationCode);
        User user = userRepository.getUserById(userActivationRequest.getUserId());
        if (user.getStatus() == UserStatus.ACTIVATED)
            throw new InvalidEmailException(ErrorMessage.ACCOUNT_HAS_ALREADY_ACTIVATED);
        user.setStatus(UserStatus.ACTIVATED);

        return userRepository.save(user);
    }
}
