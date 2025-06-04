package com.empmgmt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.empmgmt.dao.UserRepository;
import com.empmgmt.enums.UserRole;
import com.empmgmt.exception.UserNotFoundException;
import com.empmgmt.model.Users;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
        
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Users register(String name, String email, String password, UserRole role) {
        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmailId(email);
        newUser.setPassword(passwordEncoder.encode(password));  
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Users> getUnassignedEmployees() {
        return userRepository.findUnassignedEmployees(UserRole.EMPLOYEE);
    }

    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Users user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
    
    public Optional<Users> findByEmailId(String email) {
        return userRepository.findByEmailId(email);
    }

}
