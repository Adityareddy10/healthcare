package com.healthcare.backend.service;

import com.healthcare.backend.entity.User;
import com.healthcare.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long id, User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPassword() != null) {
                existingUser.setPassword(userDetails.getPassword());
            }
            if (userDetails.getRole() != null) {
                existingUser.setRole(userDetails.getRole());
            }
            if (userDetails.getActive() != null) {
                existingUser.setActive(userDetails.getActive());
            }
            return userRepository.save(existingUser);
        }
        return null;
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public void deactivateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setActive(false);
            userRepository.save(existingUser);
        }
    }
    
    public void activateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setActive(true);
            userRepository.save(existingUser);
        }
    }
}
