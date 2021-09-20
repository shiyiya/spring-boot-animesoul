package com.app.animesoul.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {

    @Column(unique = true, nullable = false, length = 8)
    private String userName;

    @Column(length = 8)
    private String nikeName;

    @Column(length = 16)
    private String password;

    @Column(length = 32)
    private String email;

    @Column(length = 32)
    private String bio;

    @Column(length = 1)
    private String role = "1";

    @OneToMany(fetch = FetchType.EAGER)
    private List<Post> posts;
}