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

    public static UserRole getNameForAuthorisationLevel(int authorisationLevel) {
        return switch (authorisationLevel) {
            case 1 -> MEMBER;
            case 2 -> LIBRARIAN;
            case 3 -> ADMIN;
            default -> GUEST;
        };
    }

    public int getAuthorisationLevel() {
        return authorisationLevel;
    }
}
