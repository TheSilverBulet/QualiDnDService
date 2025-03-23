package com.silverbullet.qualidnd.controller;

import static com.silverbullet.qualidnd.common.DDConstants.SUCCESS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.response.QDDResponse;
import com.silverbullet.qualidnd.user.QDDUser;
import com.silverbullet.qualidnd.util.CreatureUtils;

/**
 * Class to handle and encapsulate functionality relating to characters
 *
 * @author Batman
 *
 */
@QDDController
public class CharacterController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(CharacterController.class);

	private static final String NEW_CHARACTER_KEY = "newCharacter";
	private static final String CHARACTER_NAME_KEY = "characterName";
	private static final String CHARACTER_KEY = "character";

	/**
	 * Method to create a character based on the passed info
	 *
	 * @param character Character model to send to the dao
	 * @return 200 if successful, else 400
	 * @throws JSONException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@PostMapping(value = "/{username}/character/create")
	public ResponseEntity<List<CharacterDto>> createCharacter(
			@PathVariable(DDConstants.USERNAME_KEY) final String username, @RequestBody String requestString)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		final CharacterDto newCharacter = new ObjectMapper()
				.readValue(new JSONObject(requestString).get(NEW_CHARACTER_KEY).toString(), CharacterDto.class);
		newCharacter.setCurrentHealth(newCharacter.getMaxHealth());
		if (this.characterService.putCharacter(newCharacter, username)) {
			this.redisService.refreshCharacterCache();
			LOG.info("Character created");
			return ResponseEntity.ok().body(this.redisService.retrieveUserCharactersList(username));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Method to update a character
	 *
	 * @param username    the username of the character's owner
	 * @param requestJson the character information as JSON
	 * @return ResponseEntity with QDDResponse type
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws JSONException
	 */
	@PostMapping(value = "/{username}/character/update")
	public ResponseEntity<QDDResponse> updateCharacter(@PathVariable(DDConstants.USERNAME_KEY) final String username,
			@RequestBody String requestJson)
			throws JsonParseException, JsonMappingException, IOException, JSONException {
		final CharacterDto character = new ObjectMapper()
				.readValue(new JSONObject(requestJson).get(CHARACTER_KEY).toString(), CharacterDto.class);
		CreatureUtils.ensureModIntegrity(character);
		if (this.characterService.updateCharacter(character, username)) {
			this.redisService.refreshCharacterCache();
			LOG.info("Character updated");
			return ResponseEntity.ok().body(new QDDResponse("Character updated", true));
		} else {
			return ResponseEntity.badRequest().body(new QDDResponse("Issue updating character", false));
		}
	}

	/**
	 * Mapping to make the provided character the active character for the given
	 * user
	 *
	 * @param username      the username of the character's owner
	 * @param characterName the name of the character to make active
	 * @return ResponseEntity typed to List<CharacterDto>
	 */
	@GetMapping(value = "/makeActive/{username}/{characterName}")
	public ResponseEntity<List<CharacterDto>> makeActive(@PathVariable(DDConstants.USERNAME_KEY) String username,
			@PathVariable(CHARACTER_NAME_KEY) String characterName) {
		if (this.characterService.setActive(username, characterName)) {
			this.redisService.refreshCharacterCache();
			LOG.info("Character made active");
			return ResponseEntity.ok().body(this.redisService.retrieveUserCharactersList(username));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Mapping to get a specific character for a user
	 *
	 * @param username      the username of the user
	 * @param characterName the name of the character to retrieve
	 * @return the character as a CharacterDto object
	 */
	@PostMapping(value = "/getCharacter/{username}/{characterName}")
	public CharacterDto getCharacter(@PathVariable(DDConstants.USERNAME_KEY) String username,
			@PathVariable(CHARACTER_NAME_KEY) String characterName) {
		final CharacterDto cModel = this.redisService.retrieveSpecificCharacter(characterName);
		if (null != cModel) {
			LOG.info("Character retrieved");
			return cModel;
		} else {
			return new CharacterDto();
		}
	}

	/**
	 * Mapping to retrieve the user's currently active character
	 *
	 * @param username the username of the user
	 * @return the active character as a CharacterDto
	 */
	@GetMapping(value = "/getDashboardChar/{username}")
	public CharacterDto getActiveChar(@PathVariable(DDConstants.USERNAME_KEY) String username) {
		final QDDUser currentUser = this.userService.loadUserByUsername(username);
		return this.redisService.retrieveUserActiveCharacter(currentUser.getUsername());
	}

	/**
	 * Mapping to update a specific attribute of a character
	 *
	 * @param request the request as a map
	 * @return RAW responseEntity related to the success of the operation
	 */
	@PostMapping(value = "/updateCharacterAttribute")
	public ResponseEntity updateCharacterAttribute(@RequestBody Map<String, String> request) {
		final String characterName = request.get(CHARACTER_NAME_KEY);
		final String attrName = request.get("attributeName");
		final int attrValue = Integer.parseInt(request.get("attributeValue"));
		if (this.characterService.updateSingleAttribute(characterName, attrName, attrValue)) {
			this.redisService.refreshCharacterCache();
			LOG.info("Character attribute updated");
			return new ResponseEntity<>(SUCCESS, new HttpHeaders(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("FAILED! Attribute doesn't seem to exist!", new HttpHeaders(),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Mapping to delete a character
	 *
	 * @param characterName the name of the character to delete
	 * @param username      the username of the user the character belongs to
	 * @return ResponseEntity of type updated List<CharacterDto>
	 */
	@DeleteMapping(value = "/deleteCharacter/{characterName}/{username}")
	public ResponseEntity<List<CharacterDto>> deleteCharacter(
			@PathVariable(CHARACTER_NAME_KEY) final String characterName,
			@PathVariable(DDConstants.USERNAME_KEY) final String username) {
		if (this.characterService.deleteCharacter(username, characterName)) {
			this.redisService.refreshCharacterCache();
			LOG.info("Characted successfully deleted");
			return ResponseEntity.ok().body(this.redisService.retrieveUserCharactersList(username));
		} else {
			return ResponseEntity.status(204).body(new ArrayList<>());
		}
	}

	/**
	 * Mapping to get the list of all characters in the DB
	 *
	 * @return the ResponseEntity containing the list of all characters
	 */
	@GetMapping(value = "/getAllCharacters")
	public ResponseEntity<List<CharacterDto>> getAllCharacters() {
		final List<CharacterDto> allCharactersEntities = this.redisService.retrieveCharacterList();
		if (!allCharactersEntities.isEmpty()) {
			return ResponseEntity.ok().body(allCharactersEntities);
		}
		return ResponseEntity.status(204).body(null);
	}

	/**
	 * Mapping to get the list of all characters for a specific user
	 *
	 * @param username the username of the user for whom to retrieve all characters
	 * @return the list of characters as a ResponseEntity
	 */
	@GetMapping(value = "/getAllCharacters/{username}")
	public ResponseEntity<List<CharacterDto>> getAllCharactersforUser(
			@PathVariable(DDConstants.USERNAME_KEY) final String username) {
		final List<CharacterDto> userCharacters = this.redisService.retrieveUserCharactersList(username);
		if (!userCharacters.isEmpty()) {
			LOG.info("All user's characters retrieved");
			return ResponseEntity.ok().body(userCharacters);
		}
		return ResponseEntity.status(204).body(null);
	}

	/**
	 * Mapping to retrieve the character details for a specific character for a
	 * specific user
	 *
	 * @param characterName the name of the character to retrieve
	 * @param username      the username of the owner of the character to retrieve
	 * @return the character
	 */
	@GetMapping(value = "/retrieveCharacterDetails/{characterName}/{username}")
	public ResponseEntity<CharacterDto> retrieveDetails(@PathVariable(CHARACTER_NAME_KEY) final String characterName,
			@PathVariable(DDConstants.USERNAME_KEY) final String username) {
		final CharacterDto cModel = this.redisService.retrieveSpecificCharacter(characterName);
		if (cModel != null) {
			return ResponseEntity.ok().body(cModel);
		}
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
	}
}
