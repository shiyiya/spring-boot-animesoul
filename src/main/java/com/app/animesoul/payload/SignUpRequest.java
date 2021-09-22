package com.app.animesoul.payload;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@NotNull
public class SignUpRequest {
    @Size(min = 1, max = 8)
    private String userName;

    @Size(min = 8, max = 16)
    private String password;
}