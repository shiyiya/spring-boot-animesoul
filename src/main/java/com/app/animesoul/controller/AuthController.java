package com.app.animesoul.controller;

import com.app.animesoul.auth.JwtTokenProvider;
import com.app.animesoul.entities.User;
import com.app.animesoul.payload.ApiResponse;
import com.app.animesoul.payload.JwtAuthenticationResponse;
import com.app.animesoul.payload.SignUpRequest;
import com.app.animesoul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @GetMapping("/refresh_token")
    public Object refreshToken(@RequestParam String userName) {
        return userRepository.findByUserName(userName);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> register(@Valid SignUpRequest signUpRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getUserName(),
                        passwordEncoder.encode(signUpRequest.getPassword())
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Logger.getGlobal().info(authentication.toString());
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid SignUpRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity<>(ApiResponse.x(400, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getUserName(), passwordEncoder.encode(signUpRequest.getPassword()));
        User result = userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.v(result));
    }
}
