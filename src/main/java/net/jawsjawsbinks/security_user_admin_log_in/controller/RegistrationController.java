package net.jawsjawsbinks.security_user_admin_log_in.controller;

import org.springframework.web.bind.annotation.RestController;

import net.jawsjawsbinks.security_user_admin_log_in.model.SecureUser;
import net.jawsjawsbinks.security_user_admin_log_in.model.SecureUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class RegistrationController {

    // connects to the SecureUserRepository
    @Autowired
    private SecureUserRepository secureUserRepository;

    // connects to PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // encrypts the password when it's enetered
    @PostMapping("/register")
    public SecureUser creatUser(@RequestBody SecureUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return secureUserRepository.save(user);

    }
    
}
