package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.ACTIVE_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.CURRENT_HEALTH_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.DELETE_SINGLE_CHARACTER_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.DELETE_USER_CHARACTERS_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.LEVEL_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.MAX_HEALTH_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.NAME_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.PARTIAL_UPDATE_CHAR_ATTR_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.RACE_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.RETRIEVE_ALL_ACTIVE_CHARS_PARTIAL;
import static com.silverbullet.qualidnd.common.DDConstants.RETRIEVE_ALL_CHARACTERS;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;
import static com.silverbullet.qualidnd.common.DDConstants.TEMPORARY_HIT_POINTS_KEY;
import static com.silverbullet.qualidnd.common.DDConstants.UPDATE_CHARACTER_QUERY;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.util.CreatureUtils;
import com.silverbullet.qualidnd.util.SQLUtils;

/**
 * Dao class handling all DB operations relating to Characters
 *
 * @author Batman
 *
 */
@Repository
public class CharactersDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(CharactersDao.class);

	/**
	 * Method to retrieve a character from the database
	 *
	 * @param username The player name associated with the character
	 * @return The character from the table
	 */
	public CharacterDto retrieveCharacter(String username, String characterName) {
		CharacterDto character;
		try {
			character = this.template.queryForObject(DDConstants.RETRIEVE_CHARACTER_QUERY,
					new Object[] { username, characterName }, new BeanPropertyRowMapper<CharacterDto>() {
						@Override
						public CharacterDto mapRow(ResultSet rs, int rownumber) throws SQLException {
							final CharacterDto character = new CharacterDto();
							character.setName(rs.getString(NAME_KEY));
							CreatureUtils.setAbilitiesFromSql(SQLUtils.parseAbilities(rs.getString("AbilityScores")),
									character);
							character.setRace(rs.getString(RACE_KEY));
							character.setLevel(rs.getInt(LEVEL_KEY));
							character.setBaseArmorClass();
							character.setCharacterClass(SQLUtils.parseClassString(rs.getString("Classes")));
							character.setMulticlassed();
							character.setMaxHealth(rs.getInt(MAX_HEALTH_KEY));
							character.setTemporaryHitPoints(rs.getInt(TEMPORARY_HIT_POINTS_KEY));
							character.setCurrentHealth(rs.getInt(CURRENT_HEALTH_KEY));
							character.setActive(rs.getBoolean(ACTIVE_KEY));
							character.setChakra(rs.getInt("Chakra"));
							character.setOwner(username);
							return character;
						}
					});
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveCharacter: ", PROCESS_EXCEPTION, dae.getMessage()));
			character = new CharacterDto();
		}
		return character;
	}

	/**
	 * Method to retrieve a user's active character from the DB
	 *
	 * @param username the username of the user for whom to retrieve the active
	 *                 character
	 * @return the active character
	 */
	public CharacterDto retrieveActiveCharacter(String username) {
		CharacterDto character;
		try {
			character = this.template.queryForObject(DDConstants.RETRIEVE_ACTIVE_CHARACTER_QUERY,
					new Object[] { username }, new BeanPropertyRowMapper<CharacterDto>() {
						@Override
						public CharacterDto mapRow(ResultSet rs, int rownumber) throws SQLException {
							final CharacterDto character = new CharacterDto();
							character.setName(rs.getString(NAME_KEY));
							CreatureUtils.setAbilitiesFromSql(SQLUtils.parseAbilities(rs.getString("AbilityScores")),
									character);
							character.setRace(rs.getString(RACE_KEY));
							character.setLevel(rs.getInt(LEVEL_KEY));
							character.setBaseArmorClass();
							character.setCharacterClass(
									SQLUtils.parseClassString(rs.getString(DDConstants.CLASSES_KEY)));
							character.setMulticlassed();
							character.setMaxHealth(rs.getInt(MAX_HEALTH_KEY));
							character.setTemporaryHitPoints(rs.getInt(TEMPORARY_HIT_POINTS_KEY));
							character.setCurrentHealth(rs.getInt(CURRENT_HEALTH_KEY));
							character.setActive(rs.getBoolean(ACTIVE_KEY));
							character.setChakra(rs.getInt(DDConstants.CHAKRA_KEY));
							character.setOwner(username);
							return character;
						}
					});
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveActiveCharacter: ", PROCESS_EXCEPTION, dae.getMessage()));
			character = new CharacterDto();
		}
		return character;
	}

	/**
	 * Method to retrieve all of a specific user's characters
	 *
	 * @param username the username for whom to retrieve the list of characters
	 * @return the list of characters for the user
	 */
	public List<CharacterDto> retrieveUsersCharacters(String username) {
		List<CharacterDto> characterList = new ArrayList<>();
		try {
			final List<Map<String, Object>> rows = this.template
					.queryForList(DDConstants.RETRIEVE_ALL_USER_CHARACTER_QUERY, username);

			characterList = this.getCharactersFromMapList(rows);

		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveUsersCharacters: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return characterList;
	}

	/**
	 * Method to insert a new character into a table
	 *
	 * @param character The character object to insert
	 * @return true if success else false
	 */
	public boolean insertCharacterIntoTable(CharacterDto character, final String username) {
		int success;
		if (this.retrieveUsersCharacters(username).isEmpty()) {
			character.setActive(true);
		}
		try {
			success = this.template.update(DDConstants.INSERT_CHARACTER_QUERY, username, character.getName(),
					character.getRace(), character.getLevel(),
					SQLUtils.serializeClassString(character.getCharacterClass()),
					SQLUtils.serializeAbilityScores(character), character.getMaxHealth(),
					character.getTemporaryHitPoints(), character.getCurrentHealth(), character.getChakra(),
					SQLUtils.serializeSpellSlotString(character.getSpellSlots()), character.isActive(),
					character.getDeathSave().get("SUCCESSES"), character.getDeathSave().get("FAILURES"), 0, 0, 0, 0, 0,
					0);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "insertCharacterIntoTable: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Method to set a character to active status in the DB, and unset another
	 * character as active
	 *
	 * @param username      the username requesting the update
	 * @param characterName the name of the character to make active
	 * @return true if success, else false
	 */
	public boolean makeActive(String username, String characterName) {
		Integer success = null;
		try {
			final CharacterDto currentActive = this.template.queryForObject(
					DDConstants.GET_USER_CURRENT_ACTIVE_CHARACTER_QUERY, new Object[] { username },
					new BeanPropertyRowMapper<>(CharacterDto.class));
			if (currentActive != null
					&& this.template.update(DDConstants.MAKE_INACTIVE_QUERY, username, currentActive.getName()) > 0) {
				success = this.template.update(DDConstants.MAKE_CHARACTER_ACTIVE_QUERY, username, characterName);
			}
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "makeActive: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success != null && success > 0;
	}

	/**
	 * Method to update a specific character in the DB
	 *
	 * @param toUpdate the character to update
	 * @param username the username of the user requesting the update
	 * @return true if success, else false
	 */
	public boolean updateCharacter(CharacterDto toUpdate, String username) {
		int success;
		try {
			success = this.template.update(UPDATE_CHARACTER_QUERY, username, toUpdate.getName(), toUpdate.getRace(),
					SQLUtils.serializeClassString(toUpdate.getCharacterClass()), toUpdate.getLevel(),
					toUpdate.getMaxHealth(), toUpdate.getTemporaryHitPoints(), toUpdate.getCurrentHealth(),
					SQLUtils.serializeAbilityScores(toUpdate), toUpdate.getChakra(),
					SQLUtils.serializeSpellSlotString(toUpdate.getSpellSlots()), toUpdate.isActive(),
					toUpdate.getDeathSave().get("SUCCESSES"), toUpdate.getDeathSave().get("FAILURES"), 0, 0, 0, 0, 0, 0,
					// Below here is for updating on duplicate key
					toUpdate.getLevel(), toUpdate.getMaxHealth(), toUpdate.getTemporaryHitPoints(),
					toUpdate.getCurrentHealth(), SQLUtils.serializeClassString(toUpdate.getCharacterClass()),
					SQLUtils.serializeAbilityScores(toUpdate), toUpdate.isActive(),
					SQLUtils.serializeSpellSlotString(toUpdate.getSpellSlots()), toUpdate.getChakra());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "updateCharacter: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success > 0;
	}

	/**
	 * Method to delete all of a user's characters
	 *
	 * @param username the username for whom to delete all characters
	 * @return true if success, else false
	 */
	public boolean deleteUserCharacters(String username) {
		int success;
		try {
			success = this.template.update(DELETE_USER_CHARACTERS_QUERY, username);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "deleteUserCharacters: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success > 0;
	}

	/**
	 * Method to delete a single character
	 *
	 * @param username      the username of the user who owns the character
	 * @param characterName the name of the character to delete
	 * @return true if success, else false
	 */
	public boolean deleteSingleCharacter(final String username, final String characterName) {
		int success;
		try {
			final CharacterDto character = this.retrieveCharacter(username, characterName);
			if (!character.isActive()) {
				success = this.template.update(DELETE_SINGLE_CHARACTER_QUERY, username, characterName);
			} else {
				return false;
			}
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "deleteUserCharacters: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success > 0;
	}

	/**
	 * Method to retrieve the level of all selected characters in the DB
	 *
	 * @param selectedNames the list of characters to query levels for
	 * @return the list of levels
	 */
	public List<Integer> retrieveAllActiveCharLvls(List<String> selectedNames) {
		final StringBuilder fullRetrievalQuery = new StringBuilder();
		fullRetrievalQuery.append(RETRIEVE_ALL_ACTIVE_CHARS_PARTIAL);
		fullRetrievalQuery.append("name IN ");
		for (int i = 0; i < selectedNames.size(); i++) {
			if (i == 0) {
				fullRetrievalQuery.append("(");
			}
			fullRetrievalQuery.append("'");
			fullRetrievalQuery.append(selectedNames.get(i).replace("'", "\\'"));
			fullRetrievalQuery.append("'");
			if (i < selectedNames.size() - 1) {
				fullRetrievalQuery.append(",");
			}
		}
		fullRetrievalQuery.append(");");
		List<CharacterDto> characterList;
		final List<Integer> activeLevelsList = new ArrayList<>();
		try {
			final List<Map<String, Object>> rows = this.template.queryForList(fullRetrievalQuery.toString());

			characterList = this.getCharactersFromMapList(rows);

			for (final CharacterDto c : characterList) {
				activeLevelsList.add(c.getLevel());
			}
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveAllActiveCharLvls: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return activeLevelsList;
	}

	/**
	 * Method to retrieve all characters from the DB
	 *
	 * @return the list of characters
	 */
	public List<CharacterDto> retrieveAllCharacters() {
		List<CharacterDto> allChars = new ArrayList<>();
		try {
			final List<Map<String, Object>> rows = this.template.queryForList(RETRIEVE_ALL_CHARACTERS);

			allChars = this.getCharactersFromMapList(rows);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveAllCharacters: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return allChars;
	}

	/**
	 * Mapper function to map a retrieved object from the DB to a list of characters
	 *
	 * @param rows the retrieved object from the DB
	 * @return the list of characters
	 */
	private List<CharacterDto> getCharactersFromMapList(List<Map<String, Object>> rows) {
		final List<CharacterDto> characterList = new ArrayList<>();
		for (final Map<String, Object> row : rows) {
			final CharacterDto character = new CharacterDto();
			character.setName(row.get(NAME_KEY).toString());
			CreatureUtils.setAbilitiesFromSql(
					SQLUtils.parseAbilities(row.get(DDConstants.ABILITY_SCORES_KEY).toString()), character);
			character.setRace(row.get(RACE_KEY).toString());
			character.setLevel((int) row.get(LEVEL_KEY));
			character.setBaseArmorClass();
			character.setCharacterClass(SQLUtils.parseClassString(row.get(DDConstants.CLASSES_KEY).toString()));
			character.setMulticlassed();
			character.setMaxHealth((int) row.get(MAX_HEALTH_KEY));
			character.setTemporaryHitPoints((int) row.get(TEMPORARY_HIT_POINTS_KEY));
			character.setCurrentHealth((int) row.get(CURRENT_HEALTH_KEY));
			character.setActive((int) row.get(ACTIVE_KEY) == 1 ? true : false);
			character.setChakra((int) row.get(DDConstants.CHAKRA_KEY));
			character.setOwner(row.get("username").toString());
			characterList.add(character);
		}
		return characterList;
	}

	/**
	 * Method to update a character's attribute
	 *
	 * @param characterName the name of the character to update
	 * @param attrName      the attribute to update
	 * @param attrValue     the value to update
	 * @return true if success, else false
	 */
	public boolean updateAttribute(final String characterName, final String attrName, final int attrValue) {
		final StringBuilder fullQuery = new StringBuilder();
		fullQuery.append(PARTIAL_UPDATE_CHAR_ATTR_QUERY);
		fullQuery.append(attrName);
		fullQuery.append("=");
		fullQuery.append(attrValue);
		fullQuery.append(" WHERE name='");
		fullQuery.append(characterName);
		fullQuery.append("'");
		fullQuery.append(";");
		int success;
		try {
			success = this.template.update(fullQuery.toString());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "updateAttribute: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success > 0;
	}
}
