package com.app.animesoul;

import com.app.animesoul.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 支持 @CreatedDate @CreatedBy @LastModifiedDate @LastModifiedBy ...
public class AnimeSoulApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeSoulApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository) {
        return (String[] args) -> {
            // TODO: 初始化数据库角色
            //    User user1 = new User("Bob", "bob@domain.com");
            //   User user2 = new User("Jenny", "jenny@domain.com");
            //    userRepository.save(user1);
            //     userRepository.save(user2);
            //    userRepository.findAll().forEach(System.out::println);
        };
    }
}
