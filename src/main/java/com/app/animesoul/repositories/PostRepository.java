package com.app.animesoul.repositories;

import com.app.animesoul.entities.Post;
import com.app.animesoul.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByCreator(User creator);

}
