package com.app.animesoul.entities;

import com.app.animesoul.constant.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 18)
    @Enumerated(EnumType.STRING)
    private Roles name;

    @Column(length = 36)
    private String description;
}
