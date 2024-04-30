package com.example.socialmediaapp.Service;

import com.example.socialmediaapp.Entity.User;
import com.example.socialmediaapp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public boolean isValidUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public boolean isValidUserById(int userId) {
        return userRepository.existsById(userId);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean createUser(String email, String name, String password) {
        if (userRepository.existsByEmail(email)) {
            return false; // Account already exists
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(password);
        userRepository.save(newUser);
        return true; // Account created successfully
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
