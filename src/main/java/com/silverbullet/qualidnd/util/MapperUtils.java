package com.silverbullet.qualidnd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.silverbullet.qualidnd.data.dto.UserDto;
import com.silverbullet.qualidnd.enumeration.RoleEnum;
import com.silverbullet.qualidnd.user.QDDUser;

/**
 * Utility class to assist in mapping SQL query return objects to Java objects
 * 
 * @author Batman
 *
 */
public class MapperUtils {

	/**
	 * private constructor to make sure the class isn't instantiated
	 */
	private MapperUtils() {
	}

	public static List<UserDto> getUsersFromMap(List<Map<String, Object>> sqlReturnContainer) {
		final List<UserDto> userList = new ArrayList<>();
		sqlReturnContainer.forEach(user -> {
			final UserDto u = new UserDto();
			u.setId((int) user.get("uid"));
			u.setFirstName((String) user.get("FirstName"));
			u.setLastName((String) user.get("LastName"));
			u.setEmail((String) user.get("Email"));
			u.setUsername((String) user.get("UserName"));
			u.setPassword((String) user.get("Password"));
			u.setRole(RoleEnum.valueOf((String) user.get("Role")));
			userList.add(u);
		});
		return userList;
	}

	public static List<QDDUser> unpackQDDUsers(List<Map<String, Object>> sqlReturnContainer) {
		final List<QDDUser> userList = new ArrayList<>();
		sqlReturnContainer.forEach(user -> {
			final QDDUser u = new QDDUser();
			u.setUid((String) user.get("uid"));
			u.setFirstName((String) user.get("FirstName"));
			u.setLastName((String) user.get("LastName"));
			u.setEmail((String) user.get("Email"));
			u.setUsername((String) user.get("UserName"));
			u.setPassword((String) user.get("Password"));
			u.setRole(RoleEnum.valueOf((String) user.get("Role")));
			userList.add(u);
		});
		return userList;
	}

}
