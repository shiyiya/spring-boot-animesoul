package com.app.animesoul.repositories;

import com.app.animesoul.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByUserName(String userName);

//    @Query("select u from #{#entityName} u where u.userName = ?1")
//    List<User> findByUserName(String userName);
}
