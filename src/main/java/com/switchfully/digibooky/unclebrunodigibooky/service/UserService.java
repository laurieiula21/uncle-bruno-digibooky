package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import com.switchfully.digibooky.unclebrunodigibooky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        userRepository.saveUser(user);
        return user;
    }

    public boolean isUniqueInss(String inss) {
        return userRepository.isUniqueInss(inss);
    }

    public boolean isUniqueEmail(String email) {
        return userRepository.isUniqueEmail(email);
    }

    public User registerUserAsLibrarian(User memberUser) {
        User librarian = memberUser.setUserRole(UserRole.LIBRARIAN);
        User savedLibrarian = saveUser(librarian);
        return savedLibrarian;
    }

    public User getUserById(String id){
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> getUsers(){
        return null;
    }
}
