package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest {

    @Value("${server.port}")
    private int port;

    @Test
    void createUserMember_givenAUserMemberToCreate_thenTheNewlyCreatedUserMemberIsSavedAndReturned() {
        Address myAddress = new Address("Vaartstraat",61,3000,"Leuven");

        UserDto createUserMemberDto = new UserDto()
                .setInss("680-60-1053")
                .setFirstName("Dries")
                .setLastName("Verreydt")
                .setEmail("driesvv@hotmail.com")
                .setAddress(myAddress)
                .setUserRole(UserRole.MEMBER);

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
        assertThat(userDto.getUserRole()).isEqualTo(UserRole.MEMBER);

    }

}