package com.app.animesoul.service;

import com.app.animesoul.auth.UserPrincipal;
import com.app.animesoul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails findUserByUserName(String loginId) throws UsernameNotFoundException {
        return userRepository.findById(loginId).map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException("LoginId: " + loginId + " not found"));
    }
}