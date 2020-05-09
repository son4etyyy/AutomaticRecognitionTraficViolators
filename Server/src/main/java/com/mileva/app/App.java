package com.mileva.app;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Start server side
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages="com.mileva.app.rest.repo")
@EnableTransactionManagement
@EntityScan(basePackages="com.mileva.app.rest.model")
public class App {
   public static void main(String[] args) {
      run(args);
   }

   private static ConfigurableApplicationContext run(String[] args, Class<?>... moreSources) {
      return new SpringApplicationBuilder()
            .sources(App.class)
            .sources(moreSources)
            .bannerMode(Banner.Mode.OFF)
            .run(args);
   }
}
