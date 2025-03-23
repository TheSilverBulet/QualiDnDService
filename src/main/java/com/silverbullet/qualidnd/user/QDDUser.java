package com.silverbullet.qualidnd.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.silverbullet.qualidnd.enumeration.RoleEnum;

/**
 *
 * @author Batman
 *
 */
public class QDDUser extends User {

	private static final long serialVersionUID = -2948518603508143335L;

	private String uid;
	private String username;
	private String password;
	private RoleEnum role;
	private String email;
	private String FirstName;
	private String LastName;

	public QDDUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public QDDUser() {
		super("anonymous", "", false, false, false, false, Collections.emptyList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public RoleEnum getRole() {
		return this.role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getLastName() {
		return this.LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "CMSUser [uid=" + this.uid + ", userName=" + this.username + ", password=" + this.password + ", role="
				+ this.role + ", email=" + this.email + ", FirstName=" + this.FirstName + ", LastName=" + this.LastName
				+ "]";
	}

}