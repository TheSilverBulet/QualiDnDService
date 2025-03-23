package com.silverbullet.qualidnd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config class to handle the standard web configuration for the application
 *
 * @author Batman
 *
 */
@Configuration
@ComponentScan("com.silverbullet.qualidnd")
public class WebConfig implements WebMvcConfigurer {

	private static final String LOCALHOST_HOST_NAME = "localhost";
	private static final int LOCALHOST_REDIS_PORT = 6379;
	@Value("${spring.redis.host}")
	private String nonLocalRedisHost;
	@Value("${spring.redis.port}")
	private String nonLocalRedisPort;
	@Value("${spring.redis.password}")
	private String nonLocalRedisPassword;

	/**
	 * Connection Factory for Redis Cache when runnning locally
	 *
	 * @return the JedisConnectionFactory for the local Redis connection
	 */
	@Profile("local")
	@Bean
	public JedisConnectionFactory localJedisConnectionFactory() {
		final RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(LOCALHOST_HOST_NAME,
				LOCALHOST_REDIS_PORT);
		return new JedisConnectionFactory(rsc);
	}

	/**
	 * Connection Factory for Redis Cache when runnning nonlocally
	 *
	 * @return the JedisConnectionFactory for the server Redis connection
	 */
	@Profile("!local")
	@Bean
	@ConditionalOnMissingBean(JedisConnectionFactory.class)
	public JedisConnectionFactory jedisConnectionFactory() {
		final RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(this.nonLocalRedisHost,
				Integer.parseInt(this.nonLocalRedisPort));
		rsc.setPassword(this.nonLocalRedisPassword);
		return new JedisConnectionFactory(rsc);
	}

}
