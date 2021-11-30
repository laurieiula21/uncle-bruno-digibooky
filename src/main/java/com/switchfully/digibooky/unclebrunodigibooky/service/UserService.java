package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        boolean isAdded = userRepository.addUser(user);
        return user;
    }

    public boolean isUniqueInss(String inss) {
        return userRepository.isUniqueInss(inss);
    }

    public boolean isUniqueEmail(String email) {
        return userRepository.isUniqueEmail(email);
    }
}
