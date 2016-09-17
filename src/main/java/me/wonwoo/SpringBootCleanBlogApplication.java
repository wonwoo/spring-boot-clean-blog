package me.wonwoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EntityScan(basePackageClasses = {SpringBootCleanBlogApplication.class, Jsr310JpaConverters.class})
@EnableCaching
public class SpringBootCleanBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootCleanBlogApplication.class, args);
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
  }

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return cm -> cm.createCache("blog.category", initConfiguration(TEN_SECONDS));
  }

  public static final Duration TEN_SECONDS = new Duration(TimeUnit.SECONDS, 10);

  private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
    return new MutableConfiguration<>()
      .setStatisticsEnabled(true)
      .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
  }
}
