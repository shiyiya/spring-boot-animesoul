package com.app.animesoul.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {

    @Column(unique = true, nullable = false, length = 8)
    private String userName;

    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 8)
    private String nikeName;

    @Column(length = 24)
    private String email;

    @Column(length = 36)
    private String bio;

    @Column(length = 1)
    private String role = "1";

    @Column(length = 1)
    private String status = "1";

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Post> posts;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}