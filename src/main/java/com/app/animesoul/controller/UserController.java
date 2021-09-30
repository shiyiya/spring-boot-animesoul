package com.app.animesoul.controller;

import com.app.animesoul.entities.User;
import com.app.animesoul.payload.response.ApiResponse;
import com.app.animesoul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> signin(@NotBlank @PathVariable("userName") String userName) {
        final Optional<User> user = this.userRepository.findByUserName(userName);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.notfound());
        }

        return ResponseEntity.ok().body(user);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_ROOT')")
    @GetMapping("b/{userName}")
    public ResponseEntity<?> banUser(@NotBlank @PathVariable("userName") String userName) {


        return ResponseEntity.ok().body(userName);
    }
}
