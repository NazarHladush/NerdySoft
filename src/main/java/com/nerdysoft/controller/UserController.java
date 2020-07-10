package com.nerdysoft.controller;

import com.nerdysoft.dto.UserDto;
import com.nerdysoft.mapper.user.UserMapper;
import com.nerdysoft.security.UserDetailsImpl;
import com.nerdysoft.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(UserMapper userMapper, UserDetailsServiceImpl userDetailsService) {
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public UserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return userMapper.convertToDto(userDetails.getUser());
    }
}
