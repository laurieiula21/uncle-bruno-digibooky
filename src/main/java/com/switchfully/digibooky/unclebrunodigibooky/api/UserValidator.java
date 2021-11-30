package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.InvalidUserException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public UserDto validate(UserDto userDto) {
        if(userDto.getLastName() == null ||
                !isValidInss(userDto.getInss()) ||
                !isValidEmail(userDto.getEmail()) ||
                userDto.getAddress() == null ||
                userDto.getAddress().getCityName() == null ||
                userDto.getUserRole() == null){
            throw new InvalidUserException("User information given is not valid.");
        }
        return userDto;
    }

    private boolean isValidInss(String inss){
        return inss != null && userService.isUniqueInss(inss);
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^(.+)@(\\S+)$";
        return email != null && userService.isUniqueEmail(email) && email.matches(emailRegex);
    }

}
