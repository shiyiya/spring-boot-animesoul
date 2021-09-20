package com.app.animesoul.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.app.animesoul.entities.Post;
import com.app.animesoul.entities.User;
import com.app.animesoul.repositories.PostRepository;
import com.app.animesoul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DruidStatController {
    @GetMapping("/druid/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public List<Post> addNewUser(@RequestParam(required = false) String name, @RequestParam(required = false) String email) {

        User n = new User();
        n.setUserName(UUID.randomUUID().toString().substring(0, 8));
        n.setNikeName("一二三四五六七八");
        n = userRepository.save(n);

        Post post = new Post();
        post.setCreator(n);
        postRepository.save(post);

        userRepository.getById(n.getId());


        return postRepository.findAllByCreator(n);
    }
}
