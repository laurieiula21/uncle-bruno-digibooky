package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;

import java.lang.reflect.Type;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void createUserMember_givenAUserMemberToCreate_thenTheNewlyCreatedUserMemberIsSavedAndReturned() {
        Address myAddress = new Address("Vaartstraat", 61, 3000, "Leuven");

        UserDto createUserMemberDto = new UserDto()
                .setInss("680-60-1053")
                .setFirstName("Dries")
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER.toString());

        UserDto userDto =
                RestAssured
                        .given()
                        .body(createUserMemberDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/users")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(UserDto.class);

        assertThat(userDto.getId()).isNotBlank();
        assertThat(userDto.getInss()).isEqualTo("680-60-1053");
        assertThat(userDto.getFirstName()).isEqualTo("Dries");
        assertThat(userDto.getLastName()).isEqualTo("Verreydt");
        assertThat(userDto.getEmail()).isEqualTo("driesvv@hotmail.com");
        assertThat(userDto.getAddress()).isEqualTo(myAddress);
        assertThat(userDto.getUserRole()).isEqualTo(UserRole.MEMBER.toString());

    }

    @Test
    void createUserMember_givenEmptyUserToCreate_thenBadRequestResponseIsGivenWithMessage() {
        UserDto emptyUserDto = new UserDto();

        String message = RestAssured
                .given()
                .body(emptyUserDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().path("message");

        Assertions.assertThat(message).isEqualTo("User information given is not valid.");

    }

    @Test
    void createUserMember_givenWrongRole_thenBadRequestResponseIsGivenWithMessage() {
        Address myAddress = new Address("Vaartstraat", 61, 3000, "Leuven");

        UserDto createUserMemberDto = new UserDto()
                .setInss("680-60-1053")
                .setFirstName("Dries")
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(null);

        String message = RestAssured
                .given()
                .body(createUserMemberDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().path("message");

        Assertions.assertThat(message).isEqualTo("User information given is not valid.");

    }
}