package com.silverbullet.qualidnd.response;

import org.apache.commons.lang3.StringUtils;

import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.data.dto.UserDto;

/**
 * Class to represent a server response
 *
 * @author Batman
 *
 */
public class QDDResponse {

	private boolean success = false;
	private StringBuilder msg = null;
	private UserDto activeUser = null;
	private CharacterDto activeCharacter = null;

	/**
	 * Constructor
	 */
	public QDDResponse() {
		this.msg = new StringBuilder();
	}

	/**
	 * Parameter constructor
	 *
	 * @param msg
	 * @param success
	 */
	public QDDResponse(String msg, boolean success) {
		this.msg = new StringBuilder(msg);
		this.success = success;
	}

	/**
	 * Parameter constructor
	 *
	 * @param msg
	 * @param success
	 * @param activeUser
	 */
	public QDDResponse(String msg, boolean success, UserDto activeUser) {
		this.msg = new StringBuilder(msg);
		this.success = success;
		this.activeUser = activeUser;
	}

	/**
	 * Parameter constructor
	 *
	 * @param msg
	 * @param success
	 * @param activeUser
	 * @param character
	 */
	public QDDResponse(String msg, boolean success, UserDto activeUser, CharacterDto character) {
		this.msg = new StringBuilder(msg);
		this.success = success;
		this.activeUser = activeUser;
		this.activeCharacter = character;
	}

	/**
	 * Getter for the isSuccess flag
	 *
	 * @return
	 */
	public boolean isSuccess() {
		return this.success;
	}

	/**
	 * Setter for the isSuccess flag
	 *
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * Getter for the response message
	 *
	 * @return
	 */
	public String getMsg() {
		if (StringUtils.isBlank(this.msg)) {
			return StringUtils.EMPTY;
		}
		return this.msg.toString();
	}

	/**
	 * Setter for the response message
	 *
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg.replace(0, this.msg.length(), StringUtils.EMPTY);
		this.msg.append(msg);
	}

	/**
	 * Method to add Strings to the response message
	 *
	 * @param appendable
	 */
	public void addToMsg(String appendable) {
		this.msg.append(appendable);
	}

	/**
	 * Getter for the active user associated with the response
	 *
	 * @return
	 */
	public Object getActiveUser() {
		return this.activeUser;
	}

	/**
	 * Setter for the active user associated with the response
	 *
	 * @param respondingBody
	 */
	public void setActiveUser(UserDto respondingBody) {
		this.activeUser = respondingBody;
	}

	/**
	 * Getter for the active character associated with the response
	 *
	 * @return
	 */
	public CharacterDto getActiveCharacter() {
		return this.activeCharacter;
	}

	/**
	 * Setter for the active character associated with the response
	 *
	 * @param activeCharacter
	 */
	public void setActiveCharacter(CharacterDto activeCharacter) {
		this.activeCharacter = activeCharacter;
	}

	@Override
	public String toString() {
		return String.format("QDDResponse [success=%s, msg=%s, activeUser=%s, activeCharacter=%s]", this.success,
				this.msg, this.activeUser, this.activeCharacter);
	}

}