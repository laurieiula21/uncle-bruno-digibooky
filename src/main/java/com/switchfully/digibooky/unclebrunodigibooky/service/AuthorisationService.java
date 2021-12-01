package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.AuthorisationNotGrantedException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorisationService {

    private final UserService userService;
    private final Map<DigibookyFeature, UserRole> featureAuthorisations;

    public AuthorisationService(UserService userService) {
        this.userService = userService;
        featureAuthorisations = new HashMap<>();
        //Add your feature with minimum user role required for that feature here
        featureAuthorisations.put(DigibookyFeature.REGISTER_LIBRARIAN, UserRole.ADMIN);
        featureAuthorisations.put(DigibookyFeature.REGISTER_NEW_BOOK, UserRole.LIBRARIAN);

        // BookLoan Features
        featureAuthorisations.put(DigibookyFeature.RETURN_BOOK, UserRole.MEMBER);
        featureAuthorisations.put(DigibookyFeature.LEND_BOOK, UserRole.MEMBER);

        // BookFeatures
        featureAuthorisations.put(DigibookyFeature.GET_ALL_BOOKS, UserRole.GUEST);
    }

    public boolean validateAuthorisation(DigibookyFeature digibookyFeature, String authorization) {
        UserRole userRole = UserRole.GUEST;
        if(authorization != null) {
            String userEmail = parseAuthorization(authorization);
            User userByEmail = userService.getUserByEmail(userEmail);
            userRole = userByEmail.getUserRole();
        }
        if (featureAuthorisations.get(digibookyFeature).getAuthorisationLevel() <= userRole.getAuthorisationLevel()) {
            return true;
        }
        throw new AuthorisationNotGrantedException("User with role " + userRole.name().toLowerCase() +
                " does not have rights to feature " + digibookyFeature.name().toLowerCase());
    }

    public String parseAuthorization(String authorization) {
        System.out.println(authorization);
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        return username;
    }

}
