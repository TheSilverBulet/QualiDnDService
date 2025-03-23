package com.silverbullet.qualidnd.security;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.silverbullet.qualidnd.data.dao.UserDao;
import com.silverbullet.qualidnd.data.dto.UserDto;
import com.silverbullet.qualidnd.util.BCryptUtils;

/**
 * Custom authentication provider for the application
 *
 * @author Batman
 *
 */
@Component
@Configuration
public class CustomAuthProvider implements AuthenticationProvider {

	private static final Logger LOG = LogManager.getLogger(CustomAuthProvider.class);

	@Autowired
	private UserDao userDao;

	/**
	 * Method to authenticate the user for the application
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {

		final String name = authentication.getName();
		final String password = authentication.getCredentials().toString();

		if (this.authSuccess(name, password)) {
			LOG.info("successfully authorized user");
			return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
		} else {
			return null;
		}
	}

	/**
	 * Helper method to test whether or not the authentication is successful
	 *
	 * @param givenUserName the provided username to test
	 * @param givenPassword the provided password to test
	 * @return true if success, else false
	 */
	private boolean authSuccess(String givenUserName, String givenPassword) {
		final UserDto user = this.userDao.retrieveUser(givenUserName);
		return user != null && BCryptUtils.isMatch(givenPassword, user.getPassword());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
