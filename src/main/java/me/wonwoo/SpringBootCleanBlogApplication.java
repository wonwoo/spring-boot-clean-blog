package me.wonwoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import me.wonwoo.security.GitProperties;

@SpringBootApplication
@EntityScan(basePackageClasses = {SpringBootCleanBlogApplication.class, Jsr310JpaConverters.class})
@EnableCaching
@EnableConfigurationProperties({GitProperties.class})
public class SpringBootCleanBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCleanBlogApplication.class, args);
  }

}
