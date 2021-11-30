package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.InvalidUserException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public UserDto validate(UserDto userDto) {
        if(userDto.getFirstName() == null || userDto.getLastName() == null ||
                userDto.getInss() == null || userDto.getAddress() == null ||
                userDto.getUserRole() == null){
            throw new InvalidUserException("User information given is not valid.");
        }
        return userDto;
    }

}
