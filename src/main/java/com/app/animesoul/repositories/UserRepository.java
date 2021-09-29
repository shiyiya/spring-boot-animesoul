package com.app.animesoul.repositories;

import com.app.animesoul.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
//    @Query("select u from #{#entityName} u where u.userName = ?1")
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
