package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService {


    public boolean validateAuthorisation(DigibookyFeature registerLibrarian, UserRole userRole) {
        return false;
    }
}
