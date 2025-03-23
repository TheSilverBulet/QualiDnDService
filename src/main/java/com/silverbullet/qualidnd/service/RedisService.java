package com.silverbullet.qualidnd.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.cache.objects.RedisCharacterList;
import com.silverbullet.qualidnd.cache.objects.RedisInitiative;
import com.silverbullet.qualidnd.cache.objects.RedisJutsuList;
import com.silverbullet.qualidnd.cache.objects.RedisMonsterList;
import com.silverbullet.qualidnd.cache.objects.RedisSpellList;
import com.silverbullet.qualidnd.cache.repositories.CharacterListRepository;
import com.silverbullet.qualidnd.cache.repositories.InitiativeRepository;
import com.silverbullet.qualidnd.cache.repositories.JutsuRepository;
import com.silverbullet.qualidnd.cache.repositories.MonsterRepository;
import com.silverbullet.qualidnd.cache.repositories.SpellRepository;
import com.silverbullet.qualidnd.data.dto.CharacterDto;
import com.silverbullet.qualidnd.data.dto.InitiativeDto;
import com.silverbullet.qualidnd.data.dto.Jutsu;
import com.silverbullet.qualidnd.data.dto.Monster;
import com.silverbullet.qualidnd.data.dto.Spell;

/**
 * Service class to perform operations involving the Redis Cache
 *
 * @author Batman
 *
 */
@Service
public class RedisService implements Serializable {

	private static final long serialVersionUID = 8234333711991176938L;
	private static final Logger LOG = LogManager.getLogger(RedisService.class);
	private static final String NO_RECORD_FOUND_MSG = "No Record found with the given id: %s";
	private static final String NO_SPELL_RECORD_FOUND_MSG = "No Spells found";
	private static final String NO_JUTSU_RECORD_FOUND_MSG = "No Jutsu found";
	private static final String NO_MONSTER_RECORD_FOUND_MSG = "No Monsters found";
	private static final String NO_CHARACTER_RECORD_FOUND_MSG = "No Characters found";

	private static final String INIT_ID_SUB = "INIT_%s";
	private static final String SPELL_ID = "SPELLS";
	private static final String JUTSU_ID = "JUTSU";
	private static final String MONSTER_ID = "MONSTER";
	private static final String CHARACTER_LIST_ID = "CHARACTER_LIST";

	// Services
	@Autowired
	private InitiativeService initService;
	@Autowired
	private AbilityService abilityService;
	@Autowired
	private MonsterService monsterService;
	@Autowired
	private CharacterService characterService;
	// Repos
	@Autowired
	private InitiativeRepository initRepo;
	@Autowired
	private SpellRepository spellRepo;
	@Autowired
	private JutsuRepository jutsuRepo;
	@Autowired
	private MonsterRepository monsterRepo;
	@Autowired
	private CharacterListRepository characterListRepo;

	/**
	 * Method to put an Initiatve object into the cache
	 *
	 * @param toBeInserted the InitiativeDto to be inserted
	 * @param force        flag to force the put
	 */
	public void putInitiative(final InitiativeDto toBeInserted, final boolean force) {
		final String initId = buildId(toBeInserted);
		if (!this.initRepo.existsById(initId)) {
			this.initRepo.save(new RedisInitiative(initId, toBeInserted));
		} else if (force) {
			this.initRepo.deleteById(initId);
			this.initRepo.save(new RedisInitiative(initId, toBeInserted));
		} else {
			LOG.warn("Record with this ID exists already");
		}
	}

	/**
	 * Method to put an initiative list into the cache
	 *
	 * @param listToBeInserted the list to insert
	 */
	public void putAllInitiative(final List<InitiativeDto> listToBeInserted) {
		for (final InitiativeDto dto : listToBeInserted) {
			this.putInitiative(dto, true);
		}
	}

	/**
	 * Method to remove an initiative from the cache
	 *
	 * @param toBeDeleted the initiative to delete
	 */
	public void removeInitiative(final InitiativeDto toBeDeleted) {
		final String initId = buildId(toBeDeleted);
		if (this.initRepo.existsById(initId)) {
			this.initRepo.deleteById(initId);
		} else {
			LOG.warn(String.format(NO_RECORD_FOUND_MSG, toBeDeleted.getUsername()));
		}
	}

	/**
	 * Method to retrieve a single initiative object
	 *
	 * @param toBeRetrieved the initiative to retrieve
	 * @return the initiative if it exists
	 */
	public InitiativeDto retrieveIndividualInitiative(final InitiativeDto toBeRetrieved) {
		final String initId = buildId(toBeRetrieved);
		if (this.initRepo.existsById(initId)) {
			return this.initRepo.findByIdAsInitiativeDto(initId);
		}
		LOG.warn(String.format(NO_RECORD_FOUND_MSG, toBeRetrieved.getUsername()));
		return new InitiativeDto();
	}

	/**
	 * Method to retrieve all of the initiatives in the cache
	 *
	 * @return the list of initiatives in the tracker
	 */
	public List<InitiativeDto> retrieveAllInitiative() {
		LOG.info("Reloading initiative from Redis");
		final List<InitiativeDto> result = new ArrayList<>();
		this.initRepo.findAll().forEach(rI -> {
			result.add(rI.getDto());
		});
		return result;
	}

	/**
	 * Refresh the initiative cache from the DB
	 */
	public void refreshInitCache() {
		this.purgeInitiative();
		this.putAllInitiative(this.initService.fetchTable());
	}

	/**
	 * Completely destroy the initiative cache
	 */
	public void purgeInitiative() {
		this.initRepo.deleteAll();
	}

	/**
	 * Method to put an ability list in the cache based on whether the list is spell
	 * or not
	 *
	 * @param toBeInserted the list to insert
	 * @param spell        true if spell list, else Jutsu list
	 * @param force        whether or not to force the operation
	 */
	public void putAbilityList(final Object toBeInserted, final boolean spell, final boolean force) {
		if (spell) {
			if (!this.spellRepo.existsById(SPELL_ID)) {
				this.spellRepo.save(new RedisSpellList(SPELL_ID, (List<Spell>) toBeInserted));
			} else if (force) {
				this.spellRepo.deleteById(SPELL_ID);
				this.spellRepo.save(new RedisSpellList(SPELL_ID, (List<Spell>) toBeInserted));
			} else {
				LOG.warn("Record with this ID exists already");
			}
		} else {
			if (!this.jutsuRepo.existsById(JUTSU_ID)) {
				this.jutsuRepo.save(new RedisJutsuList(JUTSU_ID, (List<Jutsu>) toBeInserted));
			} else if (force) {
				this.jutsuRepo.deleteById(JUTSU_ID);
				this.jutsuRepo.save(new RedisJutsuList(JUTSU_ID, (List<Jutsu>) toBeInserted));
			} else {
				LOG.warn("Record with this ID exists already");
			}
		}
	}

	/**
	 * Remove an ability list from the cache
	 *
	 * @param spell true if spell list, else Jutsu list
	 */
	public void removeAbilityList(final boolean spell) {
		if (spell) {
			if (this.initRepo.existsById(SPELL_ID)) {
				this.initRepo.deleteById(SPELL_ID);
			} else {
				LOG.warn(NO_SPELL_RECORD_FOUND_MSG);
			}
		} else {
			if (this.jutsuRepo.existsById(JUTSU_ID)) {
				this.jutsuRepo.deleteById(JUTSU_ID);
			} else {
				LOG.warn(NO_JUTSU_RECORD_FOUND_MSG);
			}
		}
	}

	/**
	 * Method to retrieve an ability list
	 *
	 * @param spell true if retrieving spell list, else Jutsu list
	 * @return the list of abilities
	 */
	public List<?> retrieveAbilityList(final boolean spell) {
		LOG.info("Reloading ability list from Redis");
		if (spell) {
			return this.spellRepo.findByIdAsSpell(SPELL_ID);
		} else {
			return this.jutsuRepo.findByIdAsJutsu(JUTSU_ID);
		}
	}

	/**
	 * Method to refresh the ability list cache from the DB
	 */
	public void refreshAbilityCache() {
		this.purgeInitiative();
		this.putAbilityList(this.abilityService.getAbilities(true), true, true);
		this.putAbilityList(this.abilityService.getAbilities(false), false, true);
	}

	/**
	 * Method to completely destroy the ability lists in the cache
	 */
	public void purgeAbilities() {
		this.spellRepo.deleteAll();
		this.jutsuRepo.deleteAll();
	}

	/**
	 * Method to put the Monster list in the cache
	 *
	 * @param toBeInserted the monster list to insert
	 * @param force        whether or not to force the operation
	 */
	public void putMonsterList(final List<Monster> toBeInserted, final boolean force) {
		if (!this.monsterRepo.existsById(MONSTER_ID)) {
			this.monsterRepo.save(new RedisMonsterList(MONSTER_ID, toBeInserted));
		} else if (force) {
			this.monsterRepo.deleteById(MONSTER_ID);
			this.monsterRepo.save(new RedisMonsterList(MONSTER_ID, toBeInserted));
		} else {
			LOG.warn("Record with this ID exists already");
		}
	}

	/**
	 * Method to remove the monster list from the cache
	 */
	public void removeMonsterList() {
		if (this.monsterRepo.existsById(MONSTER_ID)) {
			this.monsterRepo.deleteById(MONSTER_ID);
		} else {
			LOG.warn(NO_MONSTER_RECORD_FOUND_MSG);
		}

	}

	/**
	 * Method to retrieve the monster list from the cache
	 *
	 * @return
	 */
	public List<Monster> retrieveMonsterList() {
		LOG.info("Reloading monster list from Redis");
		return this.monsterRepo.findByIdAsMonsterList(MONSTER_ID);
	}

	/**
	 * Method to retrieve the full monster list from the cache and then convert the
	 * list into a Map keyed by monster name
	 * 
	 * @return the monster list as a map
	 */
	public Map<String, Monster> retrieveMonstersAsMap() {
		final List<Monster> mons = this.retrieveMonsterList();
		final Map<String, Monster> monsAsMap = new HashMap<>();
		for (final Monster m : mons) {
			monsAsMap.put(m.getCommonName(), m);
		}
		return monsAsMap;
	}

	/**
	 * Method to refresh the monster cache from the DB
	 */
	public void refreshMonsterCache() {
		this.purgeMonsterList();
		final List<Monster> monsters = this.monsterService.getMonsterList();
		this.putMonsterList(monsters, true);
	}

	/**
	 * Method to destroy the Monster cache
	 */
	public void purgeMonsterList() {
		this.monsterRepo.deleteAll();
	}

	/**
	 * Method to put the complete list of characters in the cache
	 *
	 * @param toBeInserted the list of characters to insert
	 * @param force        whether or not to force the operation
	 */
	public void putCharacterList(final List<CharacterDto> toBeInserted, final boolean force) {
		if (!this.characterListRepo.existsById(CHARACTER_LIST_ID)) {
			this.characterListRepo.save(new RedisCharacterList(CHARACTER_LIST_ID, toBeInserted));
		} else if (force) {
			this.characterListRepo.deleteById(CHARACTER_LIST_ID);
			this.characterListRepo.save(new RedisCharacterList(CHARACTER_LIST_ID, toBeInserted));
		} else {
			LOG.warn("Record with this ID exists already");
		}
	}

	/**
	 * Method to remove the character list from the cache
	 */
	public void removeCharacterList() {
		if (this.characterListRepo.existsById(CHARACTER_LIST_ID)) {
			this.characterListRepo.deleteById(CHARACTER_LIST_ID);
		} else {
			LOG.warn(NO_CHARACTER_RECORD_FOUND_MSG);
		}

	}

	/**
	 * Method to retrieve the character list from the cache
	 *
	 * @return
	 */
	public List<CharacterDto> retrieveCharacterList() {
		LOG.info("Reloading character list from Redis");
		return this.characterListRepo.findByIdAsCharacterList(CHARACTER_LIST_ID);
	}

	/**
	 * Method to retrieve all characters for a specific user from the cache
	 *
	 * @param username the user to retrieve characters for
	 * @return the list of only the specified user's characters
	 */
	public List<CharacterDto> retrieveUserCharactersList(final String username) {
		final List<CharacterDto> all = this.retrieveCharacterList();
		final List<CharacterDto> userChars = new ArrayList<>();
		for (final CharacterDto dto : all) {
			if (StringUtils.equalsIgnoreCase(dto.getOwner(), username)) {
				userChars.add(dto);
			}
		}
		return userChars;
	}

	/**
	 * Method to retrieve a specific character from the cache
	 *
	 * @param characterName the name of the character to retrieve
	 * @return the character if found, else a blank character
	 */
	public CharacterDto retrieveSpecificCharacter(final String characterName) {
		for (final CharacterDto dto : this.retrieveCharacterList()) {
			if (StringUtils.equalsIgnoreCase(dto.getName(), characterName)) {
				return dto;
			}
		}
		return null;
	}

	/**
	 * Method to retrieve a user's active character from the cache
	 *
	 * @param username the username of the user to retrieve the active character for
	 * @return the active character for the provided user
	 */
	public CharacterDto retrieveUserActiveCharacter(final String username) {
		for (final CharacterDto dto : this.retrieveCharacterList()) {
			if (StringUtils.equalsIgnoreCase(dto.getOwner(), username) && dto.isActive()) {
				return dto;
			}
		}
		return new CharacterDto();
	}

	/**
	 * Method to refresh the character cache from the DB
	 */
	public void refreshCharacterCache() {
		this.purgeCharacterList();
		final List<CharacterDto> characters = this.characterService.fetchAllCharacters();
		this.putCharacterList(characters, true);
	}

	/**
	 * Method to destroy the Character cache
	 */
	public void purgeCharacterList() {
		this.characterListRepo.deleteAll();
	}

	/**
	 * Method to completely refresh the cache
	 */
	public void refreshCache() {
		this.refreshAbilityCache();
		this.refreshInitCache();
		this.refreshMonsterCache();
		this.refreshCharacterCache();
	}

	/**
	 * Helper method to build an ID for an initiative object to be used in the Redis
	 * cache. This way all initiative objects' id's are built in the same way and
	 * it's easy to put and retrieve objects from the cache.
	 *
	 * @param dto the dto to build the id from
	 * @return the id of the object for use with Redis Cache
	 */
	private static final String buildId(final InitiativeDto dto) {
		return String.format(INIT_ID_SUB, dto.getUsername());
	}

}
