package com.silverbullet.qualidnd.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.data.dto.ICreature;
import com.silverbullet.qualidnd.data.dto.InitiativeDto;
import com.silverbullet.qualidnd.data.dto.Monster;
import com.silverbullet.qualidnd.response.QDDResponse;
import com.silverbullet.qualidnd.util.CreatureUtils;

/**
 * Controller class to handle all functionality relating to Initiative
 *
 * @author Batman
 *
 */
@QDDController
public class InitiativeController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(InitiativeController.class);
	private static final String ADVANTAGE_KEY = "advantage";
	private static final String DISADVANTAGE_KEY = "disadvantage";
	private static final String NPC_NAME_KEY = "npcName";
	private static final String NPC_DEX_BONUS_Key = "npcDexBonus";
	private static final String NPC_HEALTH_KEY = "npcHealth";
	private static final String ROLL_TYPE_KEY = "rollType";

	/**
	 * Method to roll initiative for the provided user
	 *
	 * @param username the username of the user rolling initiative
	 * @return the response from the method
	 */
	@PostMapping("/roll/initiative/PC")
	public ResponseEntity<List<InitiativeDto>> rollPCInitiative(@RequestBody InitiativeDto initiative) {
		final CharacterDto activeCharacter = this.characterService.fetchActiveCharacter(initiative.getUsername());
		int initialRoll;
		if (StringUtils.equalsIgnoreCase(initiative.getRollType(), ADVANTAGE_KEY)) {
			initialRoll = this.getInitiativeWAdvantage();
		} else if (StringUtils.equalsIgnoreCase(initiative.getRollType(), DISADVANTAGE_KEY)) {
			initialRoll = this.getInitiativeWDisadvantage();
		} else {
			initialRoll = this.getInitiative();
		}
		final InitiativeDto init = new InitiativeDto();
		init.setModifier(CreatureUtils.getModifier(activeCharacter.getDexterity().getAbilityScore())
				+ initiative.getMiscBonus());
		init.setInitialRoll(initialRoll);
		init.setTotalRoll(initialRoll + init.getModifier());
		init.setUsername(activeCharacter.getName());
		init.setCurrentHealth(activeCharacter.getCurrentHealth());
		init.setCurrent(0);
		if (this.initService.insertInitiative(init)) {
			this.redisService.refreshInitCache();
			return ResponseEntity.ok(this.redisService.retrieveAllInitiative());
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Method to roll initiative for the provided user
	 *
	 * @param creatureName the name of the creature rolling initiative
	 * @param initBonus    the creature's init bonus
	 * @param health       the health of the creature
	 * @return the fragment with the results
	 * @throws JSONException
	 */
	@PostMapping(value = "/roll/initiative/NPC")
	public ResponseEntity<List<InitiativeDto>> rollNPCInitiative(@RequestBody String requestJson) throws JSONException {
		final String npcName = new JSONObject(requestJson).getString(NPC_NAME_KEY);
		final Integer npcDexBonus = new JSONObject(requestJson).getInt(NPC_DEX_BONUS_Key);
		final Integer npcHealth = new JSONObject(requestJson).getInt(NPC_HEALTH_KEY);
		final String rollType = new JSONObject(requestJson).getString(ROLL_TYPE_KEY);
		Integer initialRoll;
		if (StringUtils.equalsIgnoreCase(rollType, ADVANTAGE_KEY)) {
			initialRoll = this.getInitiativeWAdvantage();
		} else if (StringUtils.equalsIgnoreCase(rollType, DISADVANTAGE_KEY)) {
			initialRoll = this.getInitiativeWDisadvantage();
		} else {
			initialRoll = this.getInitiative();
		}
		final InitiativeDto model = new InitiativeDto();
		model.setInitialRoll(initialRoll);
		model.setModifier(npcDexBonus);
		model.setTotalRoll(npcDexBonus + initialRoll);
		model.setUsername(npcName);
		model.setCurrentHealth(npcHealth);
		model.setCurrent(0);
		if (this.initService.insertInitiative(model)) {
			this.redisService.refreshInitCache();
			return ResponseEntity.ok(this.redisService.retrieveAllInitiative());
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Method to clear the initiative table
	 *
	 * @return 200 if successful, else 400
	 */
	@GetMapping(value = "/table/clear")
	public ResponseEntity<QDDResponse> clearInitiativeTable() {
		if (this.initService.purgeTable()) {
			this.redisService.purgeInitiative();
			return ResponseEntity.ok().body(new QDDResponse("Table cleared", true));
		}
		return ResponseEntity.badRequest().body(new QDDResponse("Something went wrong", false));
	}

	@GetMapping(value = "/table/adjust/{name}/{health}")
	public List<InitiativeDto> adjustCreatureHealth(@PathVariable(DDConstants.NAME_KEY) String name,
			@PathVariable(DDConstants.HEALTH_KEY) Integer health) {
		if (this.initService.adjustEntityHealth(name, health)) {
			this.redisService.refreshInitCache();
			return this.redisService.retrieveAllInitiative();
		} else {
			return null;
		}
	}

	/**
	 * Method to load the initiative table
	 *
	 * @return Return the list of Initiatives to the frontend
	 */
	@GetMapping(value = "/load/initiative")
	public List<InitiativeDto> retrieveInit() {
		final List<InitiativeDto> fromDB = this.redisService.retrieveAllInitiative();
		fromDB.sort(Comparator.comparing(InitiativeDto::getTotalRoll).thenComparingInt(InitiativeDto::getInitialRoll)
				.thenComparingInt(InitiativeDto::getModifier));
		Collections.reverse(fromDB);
		return fromDB;
	}

	/**
	 * Method to set the next creature as current in the database table
	 *
	 * @return The value of the Response: SUCCESS or FAIL
	 */
	@GetMapping(value = "/table/nextCreature")
	public List<InitiativeDto> nextCreature() {
		final List<InitiativeDto> cachedList = this.redisService.retrieveAllInitiative();
		if (cachedList.size() > 1 && this.initService.advanceCursor()) {
			this.redisService.refreshInitCache();
			return this.redisService.retrieveAllInitiative();
		}
		return cachedList;
	}

	/**
	 * Mapping to delete a character from the initiative tracker
	 *
	 * @param name the name of the entity to delete from the tracker
	 * @return the updated initiative tracker after the deletion
	 */
	@DeleteMapping(value = "/table/delete/{name}")
	public List<InitiativeDto> deleteInit(@PathVariable(DDConstants.NAME_KEY) String name) {
		if (this.initService.removeEntity(name)) {
			this.redisService.removeInitiative(new InitiativeDto(name));
			return this.redisService.retrieveAllInitiative();
		}
		return null;
	}

	/**
	 * Method to retrieve the data about a specific entity in the initiative tracker
	 *
	 * @param name the name of the entity
	 * @return the entity if it exists
	 */
	@GetMapping("/table/describe/{name}")
	public ICreature getEntityDetails(@PathVariable(DDConstants.NAME_KEY) final String name) {
		final CharacterDto entity = this.redisService.retrieveSpecificCharacter(name);
		if (null != entity) {
			return entity;
		} else {
			final Map<String, Monster> monsters = this.redisService.retrieveMonstersAsMap();
			final String monsterTrueName = name.replaceAll(DDConstants.REMOVE_DIGITS_REGEX, StringUtils.EMPTY);
			return monsters.containsKey(monsterTrueName) ? monsters.get(monsterTrueName) : new Monster();
		}
	}
}
