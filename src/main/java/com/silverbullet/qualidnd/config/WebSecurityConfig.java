package com.silverbullet.qualidnd.config;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.silverbullet.qualidnd.security.CustomAuthProvider;

/**
 * Config class for web security for the application
 *
 * @author Batman
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	private static final Logger LOG = LogManager.getLogger(WebSecurityConfig.class);

	@Autowired
	private CustomAuthProvider authProvider;

	/**
	 * Set the custom auth provider as the default provider for the app
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authProvider);
	}

	/**
	 * set basic security config for app
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	/**
	 * corsConfiguration to mitigate cors-related security problems
	 *
	 * @return local CorsConfigurationSource
	 */
	@Bean
	CorsConfigurationSource localCorsConfigurationSource() {
		LOG.info("corsConfig active");
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://qualidnd.herokuapp.com",
				"http://qualidnd.herokuapp.com", "*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
		config.setAllowCredentials(true);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200", "https://qualidnd.herokuapp.com",
				"http://qualidnd.herokuapp.com").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
	}

}
