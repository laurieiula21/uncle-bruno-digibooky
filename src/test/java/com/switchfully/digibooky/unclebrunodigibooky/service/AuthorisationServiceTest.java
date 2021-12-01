package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.AuthorisationNotGrantedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootTest
class AuthorisationServiceTest {

    @Autowired
    private AuthorisationService authorisationService;

    @Test
    void givenAUser_whenAdminTriesToRegisterUserAsLibrarian_thenAccessIsGranted() {
        String myString = "Basic " + Base64.getEncoder().encodeToString("admin@mail.com:password".getBytes());
        System.out.println(myString);
        Assertions.assertThat(authorisationService.validateAuthorisation(
                DigibookyFeature.REGISTER_LIBRARIAN,myString)).isTrue();
    }

    @Test
    void givenAUser_whenMemberTriesToRegisterUserAsLibrarian_thenAccessIsDenied() {
        Assertions.assertThatExceptionOfType(AuthorisationNotGrantedException.class)
                .isThrownBy(() -> authorisationService.validateAuthorisation(DigibookyFeature.REGISTER_LIBRARIAN, null));
    }
}