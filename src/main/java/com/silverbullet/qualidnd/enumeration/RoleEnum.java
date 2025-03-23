package com.silverbullet.qualidnd.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Enumeration class to represent a user's role in the application
 *
 * @author Batman
 *
 */
public enum RoleEnum {

	USER("USER"), ADMIN("ADMIN");

	private String value = StringUtils.EMPTY;

	private RoleEnum(final String value) {
		this.value = value;
	}

	/**
	 * Getter to get the String representation of the Role
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}