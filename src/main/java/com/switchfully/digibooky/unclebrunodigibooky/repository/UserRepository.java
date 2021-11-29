package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> userList;

    public UserRepository() {
        userList = new ArrayList<>();
    }

    public UserRepository(List<User> userList) {
        this.userList = userList;
    }
}
