package com.app.animesoul.payload.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@NotNull
public class SignUpRequest /* implements Serializable */ {
    @NotBlank
    @Size(min = 1, max = 8)
    private String userName;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}