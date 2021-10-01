package com.app.animesoul;

import com.app.animesoul.repositories.UserRepository;
import com.app.animesoul.storage.StorageProperties;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 支持 @CreatedDate @CreatedBy @LastModifiedDate @LastModifiedBy ...
@EnableConfigurationProperties(StorageProperties.class)
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

    @Bean
    public TomcatServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }
}
