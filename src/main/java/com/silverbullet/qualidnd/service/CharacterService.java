package com.silverbullet.qualidnd.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.data.dao.CharactersDao;
import com.silverbullet.qualidnd.data.dto.CharacterDto;

/**
 * Service class acting as a middleman between controllers and DAO classes.
 *
 * @author Batman
 *
 */
@Service
public class CharacterService implements Serializable {

	@Autowired
	private CharactersDao charDao;

	/**
	 * Method to insert a new character into the DB
	 *
	 * @param newCharacter the character to insert
	 * @param username     the username of the owner of the character
	 * @return true if success, else false
	 */
	public boolean putCharacter(CharacterDto newCharacter, final String username) {
		return this.charDao.insertCharacterIntoTable(newCharacter, username);
	}

	/**
	 * Method to retrieve all of a specific user's characters from the DB.
	 *
	 * @param username the username of the user to retrieve characters for
	 * @return the list of user's characters
	 */
	public List<CharacterDto> fetchAllUserCharacters(final String username) {
		return this.charDao.retrieveUsersCharacters(username);
	}

	/**
	 * Method to update a specific character
	 *
	 * @param toUpdate the updated DTO for the character
	 * @param username the username of the character owner
	 * @return true if success, else false
	 */
	public boolean updateCharacter(final CharacterDto toUpdate, final String username) {
		return this.charDao.updateCharacter(toUpdate, username);
	}

	/**
	 * Method to set a user's character as their active character
	 *
	 * @param username      the username of the user
	 * @param characterName the name of the character to make active
	 * @return true if success, else false
	 */
	public boolean setActive(final String username, final String characterName) {
		return this.charDao.makeActive(username, characterName);
	}

	/**
	 * Method to retrieve a single character for a user
	 *
	 * @param username      the owner of the character
	 * @param characterName the name of the character to retrieve
	 * @return the character if they exist
	 */
	public CharacterDto fetchSingleCharacter(final String username, final String characterName) {
		return this.charDao.retrieveCharacter(username, characterName);
	}

	/**
	 * Method to retrieve the specified user's active character
	 *
	 * @param username the owner of the character
	 * @return the user's active character
	 */
	public CharacterDto fetchActiveCharacter(final String username) {
		return this.charDao.retrieveActiveCharacter(username);
	}

	/**
	 * Method to update one of a character's attributes (health, tmp hp, etc.)
	 *
	 * @param characterName  the name of the character to update
	 * @param attributeName  the name of the attribute of the character to update
	 * @param attributeValue the new value to give to the provided attribute name
	 * @return true if success, else false
	 */
	public boolean updateSingleAttribute(final String characterName, final String attributeName,
			final int attributeValue) {
		return this.charDao.updateAttribute(characterName, attributeName, attributeValue);
	}

	/**
	 * Method to retrieve all characters in the DB. Should only be called from Admin
	 * methods for admins.
	 *
	 * @return the full list of all characters in the DB
	 */
	public List<CharacterDto> fetchAllCharacters() {
		return this.charDao.retrieveAllCharacters();
	}

	/**
	 * Method to delete a user's character from the DB
	 *
	 * @param username      the username of the owner of the character
	 * @param characterName the name of the character to delete
	 * @return true if success, else false
	 */
	public boolean deleteCharacter(final String username, final String characterName) {
		return this.charDao.deleteSingleCharacter(username, characterName);
	}

	/**
	 * Method to delete all characters owned by the provided user. Typically only
	 * called when a user is deleted, as a cleanup method.
	 * 
	 * @param username the username of the owner of the characters
	 * @return true if success, else false
	 */
	public boolean deleteAllUserCharacters(final String username) {
		return this.charDao.deleteUserCharacters(username);
	}

	/**
	 * Method to read from the DB the active characters and put their total
	 * character levels into a list and return it.
	 *
	 * @param names the names of the characters' levels to retrieve
	 * @return the list of levels for the provided characters
	 */
	public List<Integer> retrieveActiveCharacterLevels(List<String> names) {
		return this.charDao.retrieveAllActiveCharLvls(names);
	}

}
