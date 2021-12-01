package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> userList;

    public UserRepository() {
        userList = new ArrayList<>();
        User adminUser = new User("1","admin","admin","admin@mail.com",
                new Address(null,0,0,"City"), UserRole.ADMIN);
        userList.add(adminUser);
    }

    public UserRepository(List<User> userList) {
        this.userList = userList;
    }

    public boolean saveUser(User user){
        userList.removeIf(member -> member.getId().equals(user.getId()));
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

    public List<User> getUserList() {
        return userList;
    }

    public User getUserById(String id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .get();
    }

    public User getUserByEmail(String email) {
        return userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .get();
    }
}
