package com.nerdysoft.controller;

import com.nerdysoft.model.UserRegistrationDto;
import com.nerdysoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public HttpStatus registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.registerUser(userRegistrationDto);
        return HttpStatus.OK;
    }

    @GetMapping("/activationUser")
    public HttpStatus activationUser(@RequestParam(name = "activationCode") String activationCode) {
        userService.activateUserByCode(activationCode);
        return HttpStatus.OK;
    }
}
