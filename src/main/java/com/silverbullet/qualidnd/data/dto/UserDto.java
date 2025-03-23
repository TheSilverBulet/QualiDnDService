package com.silverbullet.qualidnd.data.dto;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.silverbullet.qualidnd.enumeration.RoleEnum;

/**
 * Class to represent a User in the application
 *
 * @author Batman
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private RoleEnum role;
	private CharacterDto activeCharacter;

	/**
	 * No args constructor
	 */
	public UserDto() {
	}

	/**
	 * Full args constructor
	 *
	 * @param id        The id associated with the user
	 * @param firstName The firstName associated with the user
	 * @param lastName  The lastName associated with the user
	 * @param email     The email associated with the user
	 * @param userName  The username associated with the user
	 * @param password  The password associated with the user
	 * @param role      The role associated with the user
	 */
	public UserDto(int id, String firstName, String lastName, String email, String userName, String password,
			RoleEnum role) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = userName;
		this.password = password;
		this.role = role;
	}

	/**
	 * Getter for the Id of the user
	 *
	 * @return The id of the user
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter for the Id of the user
	 *
	 * @param id The id of the user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for the First Name of the user
	 *
	 * @return The user's firstName
	 */
	@JsonProperty("firstName")
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setter for the First Name of the user
	 *
	 * @param firstName The user's first name
	 */
	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for the user's last name
	 *
	 * @return The user's last name
	 */
	@JsonProperty("lastName")
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setter for the user's last name
	 *
	 * @param lastName The last name of the user
	 */
	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the user's email
	 *
	 * @return The user's email address
	 */
	@JsonProperty("email")
	public String getEmail() {
		return this.email;
	}

	/**
	 * Setter for the user's email
	 *
	 * @param email The user's email address
	 */
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for the user's username
	 *
	 * @return The user's username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter for the user's username
	 *
	 * @param username The user's username
	 */
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for the user's password
	 *
	 * @return The user's password
	 */
	@JsonProperty("password")
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter for the user's password
	 *
	 * @param password The user's password
	 */
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the user's role
	 *
	 * @return The user's role
	 */
	@JsonProperty("role")
	public RoleEnum getRole() {
		return this.role;
	}

	/**
	 * Setter for the user's role
	 *
	 * @param role The user's role
	 */
	@JsonProperty("role")
	public void setRole(RoleEnum role) {
		this.role = role;
	}

	/**
	 * Method to get the user's active character
	 *
	 * @return
	 */
	public CharacterDto getActiveCharacter() {
		return this.activeCharacter;
	}

	/**
	 * Method to set the user's active character
	 *
	 * @param activeCharacter
	 */
	public void setActiveCharacter(CharacterDto activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	/**
	 * Method to validate whether the user is an admin user or not
	 *
	 * @return
	 */
	public boolean isAdmin() {
		return this.getRole().equals(RoleEnum.ADMIN);
	}

	/**
	 * Method to check if the user is "blank", or an undefined user
	 *
	 * @return true if undefined, else false
	 */
	public boolean isBlank() {
		return StringUtils.isAllBlank(this.email, this.firstName, this.lastName, this.password, String.valueOf(this.id),
				this.username);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
				+ '\'' + ", email='" + this.email + '\'' + ", username='" + this.username + '\'' + ", password='"
				+ this.password + '\'' + ", role='" + this.role + '\'' + '}';
	}
}
