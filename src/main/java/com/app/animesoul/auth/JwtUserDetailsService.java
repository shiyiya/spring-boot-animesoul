package com.app.animesoul.auth;

import com.app.animesoul.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public JwtUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        final UserDetails user = this.userRepo.findByUserName(userName)//TODO: 使用 Jwt Token 内信息做验证
                .map(JwtUserDetail::new) //BCryptPasswordEncoder.encode(password)
                .orElseThrow(() -> new UsernameNotFoundException("userName: " + userName + " not found"));

        Logger.getGlobal().info("JwtUserDetailsService loadUserByUsername " + userName + "  " + user);

        return user;
    }
}