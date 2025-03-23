package com.silverbullet.qualidnd.controller;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.data.dto.UserDto;
import com.silverbullet.qualidnd.response.QDDResponse;

/**
 * Controller class to handle all functionality relating to login
 *
 * @author Batman
 *
 */
@QDDController
public class LoginController extends BaseController {

	private static Logger LOGGER = LogManager.getLogger(LoginController.class);

	private static final String NEW_USER_KEY = "newUser";
	private static final String NEW_CHARACTER_KEY = "newCharacter";

	/**
	 * Method to log the user into the application
	 *
	 * @param user the user to login
	 * @return ResponseEntity containing the QDDResponse relating to whether or not
	 *         the login was successful
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<QDDResponse> doLogin(@RequestBody UserDto user) {
		final QDDResponse loginResponse = new QDDResponse();
		if (StringUtils.isNotBlank(user.getUsername()) && StringUtils.isNotBlank(user.getPassword())) {
			final UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword());
			final Authentication auth = this.authManager.authenticate(authReq);
			if (auth == null) {
				loginResponse.setActiveUser(null);
				loginResponse.setSuccess(false);
				loginResponse.setMsg("Login Failed");
				return ResponseEntity.ok().body(loginResponse);
			}
			loginResponse.setSuccess(true);
			loginResponse.setMsg("Login Success!");
			final UserDto loggedInUser = this.userDao.retrieveUser(auth.getName());
			loggedInUser.setPassword("");
			loginResponse.setActiveUser(loggedInUser);
			return ResponseEntity.ok().body(loginResponse);
		}
		loginResponse.setActiveUser(new UserDto());
		return ResponseEntity.ok().body(loginResponse);
	}

	/**
	 * Mapping to register a new user, and if it's included register a character
	 * with the user.
	 *
	 * @param requestString the JSON containing the request
	 * @return ResponseEntity relating to whether or not the registration was
	 *         successful
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 */
	@PostMapping(value = "/register")
	public ResponseEntity<QDDResponse> registerUser(@RequestBody String requestString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		boolean response = false;
		if (requestString == null || StringUtils.isBlank(requestString)) {
			return ResponseEntity.badRequest().body(null);
		}
		final ObjectMapper request = new ObjectMapper();
		final JSONObject totalObject = new JSONObject(requestString);
		UserDto newUser = null;
		CharacterDto newCharacter = null;
		if (totalObject.has(NEW_USER_KEY)) {
			newUser = request.readValue(totalObject.get(NEW_USER_KEY).toString(), UserDto.class);
		} else {
			throw new ServiceException("No user data found in request");
		}
		if (totalObject.has(NEW_CHARACTER_KEY)) {
			newCharacter = request.readValue(totalObject.get(NEW_CHARACTER_KEY).toString(), CharacterDto.class);
		}
		try {
			response = this.userDao.registerUser(newUser);
		} catch (final ServiceException e) {
			LOGGER.error(e.getMessage());
			response = false;
		}
		if (response && newCharacter != null) {
			response = this.characterService.putCharacter(newCharacter, newUser.getUsername());
		}
		return response ? ResponseEntity.ok().body(new QDDResponse("Successfully registered", true))
				: ResponseEntity.status(204).body(null);

	}

}