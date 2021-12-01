package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void givenAUser_WhenRegisteringAUserAsLibrarian_ThenUsersRoleAndAccessRightsIncreasedToLibrarian(){
        User memberUser = new User("5", "firstName", "lastName",  "email",
                new Address(null,0,0,"cityName"), UserRole.MEMBER);
        User librarianUser = userService.registerUserAsLibrarian(memberUser);
        Assertions.assertThat(librarianUser.getUserRole()).isEqualTo(UserRole.LIBRARIAN);
    }

    @Test
    public void givenAUserThatDoesNotExist_whenTryingToGetItById_thenNoSuchElementExceptionIsThrown() {
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> userService.getUserById("jdoedufnhrfisj"));
    }

}