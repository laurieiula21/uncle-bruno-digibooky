package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorisationServiceTest {

    @Autowired
    private AuthorisationService authorisationService;

    @Test
    void givenAUser_whenAdminTriesToRegisterUserAsLibrarian_thenAccessIsGranted() {
        Assertions.assertThat(authorisationService.validateAuthorisation(DigibookyFeature.REGISTER_LIBRARIAN, UserRole.ADMIN)).isTrue();
    }
}