package me.wonwoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {SpringBootCleanBlogApplication.class, Jsr310JpaConverters.class})
public class SpringBootCleanBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCleanBlogApplication.class, args);
	}
}
