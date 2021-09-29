package com.app.animesoul.controller;

import com.app.animesoul.auth.JwtTokenProvider;
import com.app.animesoul.auth.JwtUserDetail;
import com.app.animesoul.constant.Roles;
import com.app.animesoul.entities.Role;
import com.app.animesoul.entities.User;
import com.app.animesoul.payload.request.SignUpRequest;
import com.app.animesoul.payload.response.ApiResponse;
import com.app.animesoul.payload.response.JwtAuthenticationResponse;
import com.app.animesoul.repositories.RoleRepository;
import com.app.animesoul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignUpRequest signUpRequest) {
        Logger.getGlobal().info("signin " + signUpRequest.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getUserName(),
                        signUpRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtTokenProvider.generateToken(authentication);
        JwtUserDetail userDetails = (JwtUserDetail) authentication.getPrincipal();
        // List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userDetails.getUser()));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        Logger.getGlobal().info("registerUser " + signUpRequest + roleRepository.findByName(Roles.ROLE_USER).isPresent());

        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity<>(ApiResponse.x(400, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUserName(), passwordEncoder.encode(signUpRequest.getPassword()));
        roleRepository.findByName(Roles.ROLE_USER).ifPresent(role -> {
            Logger.getGlobal().info("registerUser Role" + role);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        });
        User result = userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.v(result));
    }
}
