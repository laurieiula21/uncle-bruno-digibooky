package com.switchfully.digibooky.unclebrunodigibooky.domain.user;

public enum UserRole {
    GUEST(0),
    MEMBER(1),
    LIBRARIAN(2),
    ADMIN(3);

    private final int authorisationLevel;

    UserRole(int authorisationLevel) {
        this.authorisationLevel = authorisationLevel;
    }

    public int getAuthorisationLevel() {
        return authorisationLevel;
    }
}
