package me.wonwoo.config;

import java.util.concurrent.TimeUnit;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wonwoo on 2017-05-12.
 */
@Configuration
public class CacheConfig {

	public static final Duration TEN_SECONDS = new Duration(TimeUnit.SECONDS, 10);

	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
		return cm -> {
			cm.createCache("blog.category", initConfiguration(TEN_SECONDS));
			cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
		};
	}

	private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
		return new MutableConfiguration<>()
				.setStatisticsEnabled(true)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
	}
}
