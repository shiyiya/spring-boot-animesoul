package com.app.animesoul;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 支持 @CreatedDate @CreatedBy @LastModifiedDate @LastModifiedBy ...
public class AnimeSoulApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(AnimeSoulApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // TODO: 初始化数据库角色
    }
}
