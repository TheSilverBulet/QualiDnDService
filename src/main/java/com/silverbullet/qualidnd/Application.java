package com.silverbullet.qualidnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Main application class for running application
 *
 * @author Batman
 *
 */
@SpringBootApplication
@ComponentScan({ "com.silverbullet.qualidnd" })
@EnableRedisRepositories(basePackages = { "com.silverbullet.qualidnd" })
public class Application {

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
