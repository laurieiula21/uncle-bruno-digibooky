package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.InvalidUserException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import com.switchfully.digibooky.unclebrunodigibooky.repository.UserRepository;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @Test
    public void validateCorrectUserDto(){
        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto validUserDto = new UserDto()
                .setInss("680-60-1053")
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThat(userValidator.validate(validUserDto)).isEqualTo(validUserDto);
    }

    @Test
    public void invalidateUserDtoWithNoINSS(){
        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto invalidUserDtoWithNoLastName = new UserDto()
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> userValidator.validate(invalidUserDtoWithNoLastName));
    }

    @Test
    public void invalidateUserDtoWithNoUniqueINSS(){
        List<User> myUsers = new ArrayList<User>();
        myUsers.add(new User("680-60-1053",null,null,null,null,null));
        UserValidator myUserValidator = new UserValidator(new UserService(new UserRepository(myUsers)));

        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto userWithNotUniqueInss = new UserDto()
                .setLastName("Verreydt")
                .setInss("680-60-1053")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> myUserValidator.validate(userWithNotUniqueInss));
    }

    @Test
    public void invalidateUserDtoWithNoLastName(){
        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto invalidUserDtoWithNoLastName = new UserDto()
                .setInss("680-60-1053")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> userValidator.validate(invalidUserDtoWithNoLastName));
    }

    @Test
    public void invalidateUserDtoWithNoEmail(){
        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto invalidUserDtoWithNoLastName = new UserDto()
                .setInss("680-60-1053")
                .setLastName("Verreydt")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> userValidator.validate(invalidUserDtoWithNoLastName));
    }

    @Test
    public void invalidateUserDtoWithNoUniqueEmail(){
        List<User> myUsers = new ArrayList<User>();
        myUsers.add(new User("5",null,null,"driesvv@hotmail.com",null,null));
        UserValidator myUserValidator = new UserValidator(new UserService(new UserRepository(myUsers)));

        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto userWithNotUniqueEmail = new UserDto()
                .setLastName("Verreydt")
                .setInss("680-60-1053")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> myUserValidator.validate(userWithNotUniqueEmail));
    }

    @Test
    public void invalidateUserDtoWithNoValidEmailStructure(){
        Address myAddress = new Address(null, 0, 0, "Leuven");

        UserDto invalidUserDtoWithNoLastName = new UserDto()
                .setInss("680-60-1053")
                .setLastName("Verreydt")
                .setEmail("thisisnotavalidemail")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> userValidator.validate(invalidUserDtoWithNoLastName));
    }

    @Test
    public void invalidateUserDtoWithNoCityName(){
        Address myAddress = new Address(null, 0, 0, null);

        UserDto invalidUserDtoWithNoLastName = new UserDto()
                .setInss("680-60-1053")
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        assertThatExceptionOfType(InvalidUserException.class).isThrownBy( () -> userValidator.validate(invalidUserDtoWithNoLastName));
    }
}