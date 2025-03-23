package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.DELETE_USER_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.RETRIEVE_ALL_USERS_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;
import static com.silverbullet.qualidnd.common.DDConstants.UPDATE_USER_PASS_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.UPDATE_USER_USERNAME_QUERY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.data.dto.UserDto;
import com.silverbullet.qualidnd.enumeration.RoleEnum;
import com.silverbullet.qualidnd.user.QDDUser;
import com.silverbullet.qualidnd.util.BCryptUtils;
import com.silverbullet.qualidnd.util.MapperUtils;

/**
 * DAO class to handle DB operations related to User's
 *
 * @author Batman
 *
 */
@Repository
public class UserDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(UserDao.class);

	@Autowired
	CharactersDao cDao;

	/**
	 * Method to get the max id from the User table
	 *
	 * @return
	 */
	private int getMaxId() {
		int id;
		try {
			id = this.template.queryForObject(DDConstants.MAX_ID_USER_TABLE_QUERY, Integer.class);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "getMaxId: ", PROCESS_EXCEPTION, dae.getMessage()));
			id = -9;
		}
		return id;
	}

	/**
	 * Method to retrieve a user based on their username
	 *
	 * @param userName username of the user to retrieve
	 * @return The user with the associated username
	 */
	public UserDto retrieveUser(String userName) {
		UserDto user;
		try {
			user = this.template.queryForObject(DDConstants.RETRIEVE_USER_QUERY, new Object[] { userName },
					new BeanPropertyRowMapper<>(UserDto.class));
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveUser: ", PROCESS_EXCEPTION, dae.getMessage()));
			user = new UserDto();
		}
		return user;
	}

	/**
	 * Method to insert a user into the user table
	 *
	 * @param user The user to be inserted
	 * @return true if success, else false
	 */
	public boolean registerUser(UserDto user) {
		int success;
		try {
			success = this.template.update(DDConstants.INSERT_USER_QUERY, this.getMaxId() + 1, user.getUsername(),
					user.getFirstName(), user.getLastName(), user.getEmail(), BCryptUtils.encrpyt(user.getPassword()),
					user.getRole().getValue());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "registerUser: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Method to reset a user's password using the partially filled-out UserDto
	 * object
	 *
	 * @param user the partially filled out object containing the required
	 *             information in order to reset the password
	 * @return true if success, else false
	 */
	public boolean resetUserPassword(UserDto user) {
		int success;
		try {
			success = this.template.update(UPDATE_USER_PASS_QUERY, BCryptUtils.encrpyt(user.getPassword()),
					user.getUsername(), user.getFirstName());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "resetUserPassword: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Method to reset a user's username
	 *
	 * @param user        the partially filled out UserDto object containing the
	 *                    required information to validate the user against
	 * @param newUsername the new username to assign to the given user
	 * @return true if success, else false
	 */
	public boolean resetUsername(UserDto user, final String newUsername) {
		int success;
		int fullSuccess = -1;
		boolean shouldAdjustCharacters = true;
		final List<CharacterDto> chars = this.cDao.retrieveUsersCharacters(user.getUsername());
		if (chars.isEmpty()) {
			shouldAdjustCharacters = false;
		}
		try {
			success = this.template.update(UPDATE_USER_USERNAME_QUERY, newUsername, user.getUsername(),
					user.getFirstName(), user.getLastName());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "resetUserUsername: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		if (success >= 0 && shouldAdjustCharacters) {
			try {
				for (final CharacterDto dto : chars) {
					fullSuccess = this.template.update(DDConstants.UPDATE_CHARACTER_USERNAME_QUERY, newUsername,
							dto.getName());
				}
			} catch (final DataAccessException dae) {
				LOG.error(String.format(STRING_SUB3, "resetUserUsername: ", PROCESS_EXCEPTION, dae.getMessage()));
				fullSuccess = -999;
			}
		}
		return fullSuccess >= 0 || success >= 0 && !shouldAdjustCharacters;
	}

	/**
	 * Method to retrieve all users from the DB
	 *
	 * @return the list of all users
	 */
	public List<UserDto> retrieveAllUsers() {
		List<UserDto> users = new ArrayList<>();
		try {
			users = this.template.query(RETRIEVE_ALL_USERS_QUERY,
					(result, rowNum) -> new UserDto(result.getInt(DDConstants.ID_KEY),
							result.getString(DDConstants.FIRST_NAME_KEY), result.getString(DDConstants.LAST_NAME_KEY),
							result.getString(DDConstants.EMAIL_KEY), result.getString(DDConstants.USERNAME_KEY),
							result.getString(DDConstants.PASSWORD_KEY),
							RoleEnum.valueOf(result.getString(DDConstants.ROLE_KEY))));
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveAllUsers: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return users;
	}

	/**
	 * Method to delete a user from the DB
	 *
	 * @param username the username to delete
	 * @return true if success, else false
	 */
	public boolean deleteUser(String username) {
		int success;
		try {
			success = this.template.update(DELETE_USER_QUERY, username);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "deleteUser: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success > 0;
	}

	/**
	 * Method to retrieve the currently active user. Essentially used to pull a
	 * user's data from the DB when they login
	 *
	 * @param username the username to retrieve
	 * @return the currently logged-in user
	 */
	public QDDUser getActiveUser(String username) {
		QDDUser activeUser;
		try {
			final List<Map<String, Object>> sqlReturnContainer = this.template
					.queryForList(String.format("SELECT * FROM User WHERE UserName='%s';", username));
			if (!sqlReturnContainer.isEmpty()) {
				activeUser = MapperUtils.unpackQDDUsers(sqlReturnContainer).get(0);
			} else {
				throw new ServiceException("There was no user to retrieve.");
			}
		} catch (final DataAccessException | ServiceException e) {
			LOG.error("Could not retrieve user with username: " + username + "\n" + e.getMessage());
			activeUser = new QDDUser();
		}
		return activeUser;
	}
}
