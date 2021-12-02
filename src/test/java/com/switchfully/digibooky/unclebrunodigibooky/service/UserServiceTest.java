package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.UserMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    public void givenAUserThatExists_whenTryingToGetItById_thenUserIsFoundAndReturned() {
        Address myAddress = new Address(null,0,0,"cityName");
        UserDto userDto = new UserDto().setInss("5").setLastName("lastName").setEmail("email@mail.com")
                .setAddress(myAddress).setUserRole("MEMBER");
        User user = new UserMapper().mapToUser(userDto);
        String userId = user.getId();
        userService.saveUser(user);
        Assertions.assertThat(userService.getUserById(userId)).isEqualTo(user);
    }

    @Test
    public void givenAUserThatDoesNotExist_whenTryingToGetItById_thenNoSuchElementExceptionIsThrown() {
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> userService.getUserById("jdoedufnhrfisj"));
    }

    @Test
    public void givenAUserThatExists_whenTryingToGetItByEmail_thenUserIsFoundAndReturned() {
        User memberUser = new User("5", "firstName", "lastName",  "email@mail.com",
                new Address(null,0,0,"cityName"), UserRole.MEMBER);
        userService.saveUser(memberUser);
        Assertions.assertThat(userService.getUserByEmail("email@mail.com")).isEqualTo(memberUser);
    }

    @Test
    public void givenAUserThatDoesNotExist_whenTryingToGetItByEmail_thenNoSuchElementExceptionIsThrown() {
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> userService.getUserByEmail("nouser@mail.com"));
    }

    @Test
    public void given_whenTryingToGetAllUsers_thenGiveAListOfAllUsers(){
        User memberUser1 = new User("2", "firstName1", "lastName1",  "user1@mail.com",
                new Address(null,0,0,"cityName1"), UserRole.MEMBER);
        User memberUser2 = new User("3", "firstName2", "lastName2",  "user1@mail.com",
                new Address(null,0,0,"cityName2"), UserRole.LIBRARIAN);
        User memberUser3 = new User("4", "firstName3", "lastName3",  "user1@mail.com",
                new Address(null,0,0,"cityName3"), UserRole.MEMBER);
        userService.saveUser(memberUser1);
        userService.saveUser(memberUser2);
        userService.saveUser(memberUser3);

        Assertions.assertThat(userService.getUsers()).contains(memberUser1);
        Assertions.assertThat(userService.getUsers()).contains(memberUser2);
        Assertions.assertThat(userService.getUsers()).contains(memberUser3);
    }

}