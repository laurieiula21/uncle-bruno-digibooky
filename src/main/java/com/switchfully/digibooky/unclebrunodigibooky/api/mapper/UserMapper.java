package com.switchfully.digibooky.unclebrunodigibooky.api.mapper;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.UserMapperException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto){
        User user = new User(userDto.getInss(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getEmail(),
                userDto.getAddress(), UserRole.valueOf(userDto.getUserRole()));
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
                .setUserRole(user.getUserRole().toString());
        return userDto;
    }

    public UserDto mapToUserDtoWithoutInss(User user){
        UserDto userDto = new UserDto()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setAddress(user.getAddress())
                .setUserRole(user.getUserRole().toString());
        return userDto;
    }

}
