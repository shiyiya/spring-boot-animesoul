package com.app.animesoul.payload.response;

import com.app.animesoul.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JwtAuthenticationResponse implements Serializable {
    private String accessToken;
    @JsonIgnore
    private String tokenType = "Bearer ";
    private User user;

    public JwtAuthenticationResponse(String accessToken, User user) {
        this.accessToken = tokenType + accessToken;
        this.user = user;
    }
}
