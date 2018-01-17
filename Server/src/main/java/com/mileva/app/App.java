package com.mileva.app;

import com.mileva.app.rest.configuration.TrafficViolatorsConfiguration;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Start server side
 */
@Import(TrafficViolatorsConfiguration.class)
@EnableAutoConfiguration
@EnableJpaRepositories("com.mileva.app.rest.repo")
@ComponentScan(basePackages = "com.mileva.app")
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
