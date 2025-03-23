package com.silverbullet.qualidnd.service;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.data.dao.UserDao;
import com.silverbullet.qualidnd.user.QDDUser;

/**
 * Service class to perform middleman operations between controllers and the
 * UserDao
 *
 * @author Batman
 *
 */
@Service
public class QDDUserService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = -7416565797830185111L;
	@Autowired
	private UserDao uDao;

	@Override
	public QDDUser loadUserByUsername(String username) {
		QDDUser user;
		if (StringUtils.isNotBlank(username)) {
			user = this.uDao.getActiveUser(username);
		} else {
			user = new QDDUser();
		}
		return user;
	}

}