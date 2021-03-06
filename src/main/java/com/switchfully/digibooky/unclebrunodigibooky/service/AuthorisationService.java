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

        // User Features
        featureAuthorisations.put(DigibookyFeature.REGISTER_USER, UserRole.GUEST);
        featureAuthorisations.put(DigibookyFeature.REGISTER_LIBRARIAN, UserRole.ADMIN);
        featureAuthorisations.put(DigibookyFeature.GET_ALL_USERS, UserRole.ADMIN);

        // BookLoan Features
        featureAuthorisations.put(DigibookyFeature.RETURN_BOOK, UserRole.MEMBER);
        featureAuthorisations.put(DigibookyFeature.LEND_BOOK, UserRole.MEMBER);
        featureAuthorisations.put(DigibookyFeature.GET_ALL_BORROWED_BOOKS_OF_USER, UserRole.LIBRARIAN);
        featureAuthorisations.put(DigibookyFeature.GET_ALL_OVERDUE_BOOKS, UserRole.LIBRARIAN);

        // BookFeatures
        featureAuthorisations.put(DigibookyFeature.GET_ALL_BOOKS, UserRole.GUEST);
        featureAuthorisations.put(DigibookyFeature.SEARCH_BOOK_BY_ISBN, UserRole.GUEST);
        featureAuthorisations.put(DigibookyFeature.SEARCH_BOOK_BY_TITLE, UserRole.GUEST);
        featureAuthorisations.put(DigibookyFeature.SEARCH_BOOK_BY_AUTHOR, UserRole.GUEST);
        featureAuthorisations.put(DigibookyFeature.REGISTER_NEW_BOOK, UserRole.LIBRARIAN);
        featureAuthorisations.put(DigibookyFeature.DELETE_BOOK, UserRole.LIBRARIAN);
        featureAuthorisations.put(DigibookyFeature.UPDATE_BOOK, UserRole.LIBRARIAN);
        featureAuthorisations.put(DigibookyFeature.GET_ENHANCED_BOOK, UserRole.MEMBER);
        featureAuthorisations.put(DigibookyFeature.GET_BOOK, UserRole.GUEST);
    }

    public boolean getAuthorisationLevel(DigibookyFeature digibookyFeature, String authorization) {

        if (featureAuthorisations.get(digibookyFeature).getAuthorisationLevel() <= getAuthorisationLevel(authorization)) {
            return true;
        }
        throw new AuthorisationNotGrantedException("User with role " + UserRole.getNameForAuthorisationLevel(getAuthorisationLevel(authorization)).name().toLowerCase() +
                " does not have rights to feature " + digibookyFeature.name().toLowerCase());
    }

    public int getAuthorisationLevel(String authorization) {
        UserRole userRole = UserRole.GUEST;
        if(authorization != null) {
            String userEmail = parseAuthorization(authorization);
            User userByEmail = userService.getUserByEmail(userEmail);
            userRole = userByEmail.getUserRole();
        }
        return userRole.getAuthorisationLevel();
    }

    public String parseAuthorization(String authorization) {
        System.out.println(authorization);
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        return username;
    }

}
