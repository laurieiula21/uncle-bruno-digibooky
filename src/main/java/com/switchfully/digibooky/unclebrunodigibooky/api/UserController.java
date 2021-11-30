package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.UserMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorisationService authorisationService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, AuthorisationService authorisationService, UserMapper userMapper) {
        this.userService = userService;
        this.authorisationService = authorisationService;
        this.userMapper = userMapper;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        User savedUser = userService.saveUser(user);
        return userMapper.mapToUserDto(savedUser);
    }
}
