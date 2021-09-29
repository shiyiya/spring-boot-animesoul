package com.app.animesoul.repositories;

import com.app.animesoul.constant.Roles;
import com.app.animesoul.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Roles name);
}
