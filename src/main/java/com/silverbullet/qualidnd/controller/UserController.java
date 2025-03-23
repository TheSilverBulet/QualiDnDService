package com.silverbullet.qualidnd.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.UserDto;
import com.silverbullet.qualidnd.response.QDDResponse;
import com.silverbullet.qualidnd.user.QDDUser;

/**
 * Controller class to handle all user related functionality
 *
 * @author Batman
 *
 */
@QDDController
public class UserController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(UserController.class);

	private static final String FIRST_NAME_KEY = "firstname";
	private static final String NEW_PASSWORD_KEY = "newPassword";
	private static final String LAST_NAME_KEY = "lastName";
	private static final String NEW_USERNAME_KEY = "newUsername";
	private static final String OLD_USERNAME_KEY = "oldUsername";

	/**
	 * Mapping to reset a user's password
	 *
	 * @param requestJson the request JSON String used to reset the user's password
	 * @return ResponseEntity of type QDDResponse relating to the success of the
	 *         operation
	 * @throws JSONException
	 */
	@PostMapping(value = "/registration/passwordReset")
	public ResponseEntity<QDDResponse> resetPassword(@RequestBody String requestJson) throws JSONException {
		LOG.info("Preparing to reset password");
		final JSONObject jObj = new JSONObject(requestJson).getJSONObject("resetObj");
		final String username = jObj.get(DDConstants.USERNAME_KEY).toString();
		final String firstname = jObj.get(FIRST_NAME_KEY).toString();
		final String newPassword = jObj.get(NEW_PASSWORD_KEY).toString();
		if (StringUtils.isAnyBlank(username, firstname, newPassword)) {
			return ResponseEntity.badRequest().body(new QDDResponse("Failed to reset password", false));
		}
		final UserDto passReset = new UserDto();
		passReset.setFirstName(firstname);
		passReset.setUsername(username);
		passReset.setPassword(newPassword);
		if (this.userDao.resetUserPassword(passReset)) {
			return ResponseEntity.ok().body(new QDDResponse("Successfully reset password", true));
		} else {
			return ResponseEntity.badRequest().body(new QDDResponse("Error resetting password", false));
		}
	}

	/**
	 * Mapping to reset a user's username
	 *
	 * @param requestJson the request JSON String used to reset the user's username
	 * @return ResponseEntity of type QDDResponse relating to the success of the
	 *         operation
	 * @throws JSONException
	 */
	@PostMapping(value = "/registration/usernameReset")
	public ResponseEntity<QDDResponse> resetUsername(@RequestBody String requestJson) throws JSONException {
		LOG.info("Preparing to reset username");
		final JSONObject jObj = new JSONObject(requestJson).getJSONObject("resetObj");
		final String newUsername = jObj.getString(NEW_USERNAME_KEY);
		final String oldUsername = jObj.getString(OLD_USERNAME_KEY);
		final String firstname = jObj.get(FIRST_NAME_KEY).toString();
		final String lastName = jObj.getString(LAST_NAME_KEY);
		if (StringUtils.isAnyBlank(newUsername, firstname, oldUsername, lastName)) {
			return ResponseEntity.badRequest().body(new QDDResponse("Failed to reset username", false));
		}
		final UserDto usernameReset = new UserDto();
		usernameReset.setFirstName(firstname);
		usernameReset.setUsername(oldUsername);
		usernameReset.setLastName(lastName);
		if (this.userDao.resetUsername(usernameReset, newUsername)) {
			return ResponseEntity.ok().body(new QDDResponse("Successfully reset username", true));
		} else {
			return ResponseEntity.badRequest().body(new QDDResponse("Error resetting username", false));
		}
	}

	/**
	 * Mapping to delete a user
	 *
	 * @param requestJson the request JSON String used to delete the user
	 * @return ResponseEntity of type QDDResponse relating to the success of the
	 *         operation
	 * @throws JSONException
	 */
	@PostMapping(value = "/user/delete")
	public ResponseEntity<List<UserDto>> deleteUser(@RequestBody String requestJson) throws JSONException {
		final String username = new JSONObject(requestJson).get("user").toString();
		if (StringUtils.isNotBlank(username)) {
			if (this.characterService.deleteAllUserCharacters(username)) {
				if (this.userDao.deleteUser(username)) {
					return this.getAllUsers();
				} else {
					return ResponseEntity.badRequest().body(null);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		}
		return ResponseEntity.badRequest().body(null);
	}

	/**
	 * Mapping to retrieve a user
	 *
	 * @param username the username of the user to retrieve
	 * @return ResponseEntity of type QDDResponse relating to the success of the
	 *         operation
	 */
	@GetMapping(value = "/user/{username}")
	public ResponseEntity<QDDUser> getUser(@PathVariable(DDConstants.USERNAME_KEY) String username) {
		return new ResponseEntity<>(this.userService.loadUserByUsername(username), HttpStatus.OK);
	}

	/**
	 * Mapping to retrieve all users
	 *
	 * @return ResponseEntity of type List<UserDto> relating to the success of the
	 *         operation
	 */
	@GetMapping(value = "/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		final List<UserDto> users = this.userDao.retrieveAllUsers();
		if (!users.isEmpty()) {
			return ResponseEntity.ok().body(users);
		}
		return ResponseEntity.badRequest().body(null);
	}
}
