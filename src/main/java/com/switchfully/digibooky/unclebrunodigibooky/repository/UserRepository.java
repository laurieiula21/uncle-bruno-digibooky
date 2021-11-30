package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
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

    public boolean addUser(User user){
        return userList.add(user);
    }

    public boolean isUniqueInss(String inss) {
        return userList.stream()
                .noneMatch(user -> user.getInss().equals(inss));
    }

    public boolean isUniqueEmail(String email) {
        return userList.stream()
                .noneMatch(user -> user.getEmail().equals(email));
    }
}
