package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.AuthorisationNotGrantedException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorisationService {

    private final Map<DigibookyFeature, UserRole> featureAuthorisations;

    public AuthorisationService() {
        featureAuthorisations = new HashMap<>();
        //Add your feature with minimum user role required for that feature here
        featureAuthorisations.put(DigibookyFeature.REGISTER_LIBRARIAN, UserRole.ADMIN);
    }

    public boolean validateAuthorisation(DigibookyFeature digibookyFeature, UserRole userRole) {
        if (featureAuthorisations.get(digibookyFeature).getAuthorisationLevel() <= userRole.getAuthorisationLevel()) {
            return true;
        }
        throw new AuthorisationNotGrantedException("User with role " + userRole.name().toLowerCase() +
                " does not have rights to feature " + digibookyFeature.name().toLowerCase());
    }

    public String parseAuthorization(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        return username;
    }

}
