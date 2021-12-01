package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.UserMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorisationService authorisationService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final Logger myLogger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, AuthorisationService authorisationService, UserMapper userMapper, UserValidator userValidator) {
        this.userService = userService;
        this.authorisationService = authorisationService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody UserDto userDto){
        UserDto validatedUserDto = userValidator.validate(userDto);
        User user = userMapper.mapToUser(validatedUserDto);
        User savedUser = userService.saveUser(user);
        return userMapper.mapToUserDto(savedUser);
    }

    /*

     */
    @PutMapping(path = "/register-librarian/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDto registerLibrarian(@PathVariable("id") String id, @RequestHeader(required = false) String authorization){
        myLogger.info("Register librarian method called");
        authorisationService.validateAuthorisation(DigibookyFeature.REGISTER_LIBRARIAN,authorization);
        User userMember = userService.getUserById(id);
        User userLibrarian = userService.registerUserAsLibrarian(userMember);
        UserDto userDto = userMapper.mapToUserDto(userLibrarian);
        myLogger.info("Register librarian method succesfully concluded");
        return userDto;
    }
}
