package com.app.animesoul.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Email
    @Column(length = 24)
    private String email;

    @Column(length = 36)
    private String bio;

    @Column(length = 1)
    private String status = "1";

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
//    @JoinColumn(foreignKey = @javax.persistence.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Post> posts = new ArrayList<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}