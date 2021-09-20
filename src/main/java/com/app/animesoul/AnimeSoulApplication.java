package com.app.animesoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 支持 @CreatedDate @CreatedBy @LastModifiedDate @LastModifiedBy ...
public class AnimeSoulApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeSoulApplication.class, args);
    }

}
