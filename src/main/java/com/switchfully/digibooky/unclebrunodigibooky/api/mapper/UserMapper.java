package com.switchfully.digibooky.unclebrunodigibooky.api.mapper;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.UserMapperException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto){
        User user = new User(userDto.getInss(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getEmail(),
                userDto.getAddress(), userDto.getUserRole());
        return user;
    }

    public UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto()
                .setId(user.getId())
                .setInss(user.getInss())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setAddress(user.getAddress())
                .setUserRole(user.getUserRole());
        return userDto;
    }
}
