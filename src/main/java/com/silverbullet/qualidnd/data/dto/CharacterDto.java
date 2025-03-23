package com.silverbullet.qualidnd.data.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a full D&D character
 *
 * @author Batman
 *
 */
public class CharacterDto implements ICreature {

	private static final Logger LOG = LogManager.getLogger(CharacterDto.class);

	private String name = StringUtils.EMPTY;
	private String race = StringUtils.EMPTY;
	private Integer level = 1;
	private Integer baseArmorClass = 10;
	private Integer maxHealth = 0;
	private Integer currentHealth = 0;
	private Integer temporaryHitPoints = 0;
	private List<CharacterClass> characterClass = new ArrayList<>();
	private List<SpellSlots> spellSlots = new ArrayList<>();
	private boolean isMulticlassed = false;
	private boolean active = false;
	private Ability strength;
	private Ability dexterity;
	private Ability constitution;
	private Ability intelligence;
	private Ability wisdom;
	private Ability charisma;
	private Integer chakra = 0;
	private Map<String, Integer> deathSave = new HashMap<>();
	private Map<String, String> userSetValues = new HashMap<>();
	private String owner = StringUtils.EMPTY;

	private static final String SUCCESSES = "SUCCESSES";
	private static final String FAILURES = "FAILURES";

	/**
	 * Constructor with necessary setup methods
	 */
	public CharacterDto() {
		this.deathSave = this.prepareDeathSaveMap();
		this.baseArmorClass = 10 + (this.dexterity != null ? this.dexterity.getAbilityModifier() : 0);
	}

	/**
	 * Getter for name
	 *
	 * @return the name of the character
	 */
	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for name
	 *
	 * @param name the name of the character
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for race
	 *
	 * @return the race of the character
	 */
	@JsonProperty("race")
	public String getRace() {
		return this.race;
	}

	/**
	 * Setter for race
	 *
	 * @param race the race of the character
	 */
	@JsonProperty("race")
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * Getter for level
	 *
	 * @return the level of the character
	 */
	@JsonProperty("level")
	public int getLevel() {
		return this.level;
	}

	/**
	 * Setter for level
	 *
	 * @param level the level of the character
	 */
	@JsonProperty("level")
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Getter for max health
	 *
	 * @return the max health
	 */
	@JsonProperty("maxHealth")
	public int getMaxHealth() {
		return this.maxHealth;
	}

	@JsonProperty("maxHealth")
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * Getter for current health
	 *
	 * @return the current health of the character
	 */
	@JsonProperty("currentHealth")
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	/**
	 * Setter for the current health
	 *
	 * @param currentHealth the current health of the character
	 */
	@JsonProperty("currentHealth")
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	/**
	 * Getter for the temporary hit points
	 *
	 * @return the temporary hit points
	 */
	@JsonProperty("temporaryHitPoints")
	public int getTemporaryHitPoints() {
		return this.temporaryHitPoints;
	}

	/**
	 * Setter for the temporary hit points
	 *
	 * @param temporaryHitPoints
	 */
	@JsonProperty("temporaryHitPoints")
	public void setTemporaryHitPoints(int temporaryHitPoints) {
		this.temporaryHitPoints = temporaryHitPoints;
	}

	/**
	 * Getter for character classes
	 *
	 * @return
	 */
	@JsonProperty("characterClass")
	public List<CharacterClass> getCharacterClass() {
		return this.characterClass;
	}

	/**
	 * Setter for character classes
	 *
	 * @param characterClass
	 */
	@JsonProperty("characterClass")
	public void setCharacterClass(List<CharacterClass> characterClass) {
		this.characterClass = characterClass;
	}

	/**
	 * Helper method to determine if the current character is multiclassed
	 *
	 * @return
	 */
	@JsonProperty("isMulticlassed")
	public boolean getIsMulticlassed() {
		return this.characterClass.size() > 1;
	}

	/**
	 * Setter for the isMulticlassed member
	 */
	@JsonProperty("isMulticlassed")
	public void setMulticlassed() {
		this.isMulticlassed = false;
		if (this.characterClass.size() > 1) {
			this.isMulticlassed = true;
		}
	}

	/**
	 * Getter for isActive
	 *
	 * @return
	 */
	@JsonProperty("isActive")
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Setter for isActive
	 *
	 * @param active
	 */
	@JsonProperty("isActive")
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Getter for Strength
	 */
	@Override
	@JsonProperty("strength")
	public Ability getStrength() {
		return this.strength;
	}

	/**
	 * Setter for Strength
	 */
	@Override
	@JsonProperty("strength")
	public void setStrength(Ability strength) {
		this.strength = strength;
	}

	/**
	 * Getter for Dexterity
	 */
	@Override
	@JsonProperty("dexterity")
	public Ability getDexterity() {
		return this.dexterity;
	}

	/**
	 * Setter for Dexterity
	 */
	@Override
	@JsonProperty("dexterity")
	public void setDexterity(Ability dexterity) {
		this.dexterity = dexterity;
	}

	/**
	 * Getter for Constitution
	 */
	@Override
	@JsonProperty("constitution")
	public Ability getConstitution() {
		return this.constitution;
	}

	/**
	 * Setter for Constitution
	 */
	@Override
	@JsonProperty("constitution")
	public void setConstitution(Ability constitution) {
		this.constitution = constitution;
	}

	/**
	 * Getter for Intelligence
	 */
	@Override
	@JsonProperty("intelligence")
	public Ability getIntelligence() {
		return this.intelligence;
	}

	/**
	 * Setter for Intelligence
	 */
	@Override
	@JsonProperty("intelligence")
	public void setIntelligence(Ability intelligence) {
		this.intelligence = intelligence;
	}

	/**
	 * Getter for Wisdom
	 */
	@Override
	@JsonProperty("wisdom")
	public Ability getWisdom() {
		return this.wisdom;
	}

	/**
	 * Setter for Wisdom
	 */
	@Override
	@JsonProperty("wisdom")
	public void setWisdom(Ability wisdom) {
		this.wisdom = wisdom;
	}

	/**
	 * Getter for Charisma
	 */
	@Override
	@JsonProperty("charisma")
	public Ability getCharisma() {
		return this.charisma;
	}

	/**
	 * Setter for Charisma
	 */
	@Override
	@JsonProperty("charisma")
	public void setCharisma(Ability charisma) {
		this.charisma = charisma;
	}

	/**
	 * Getter for Chakra
	 *
	 * @return
	 */
	@JsonProperty("chakra")
	public Integer getChakra() {
		return this.chakra;
	}

	/**
	 * Setter for Chakra
	 *
	 * @param chakra
	 */
	@JsonProperty("chakra")
	public void setChakra(Integer chakra) {
		this.chakra = chakra;
	}

	/**
	 * Getter for the death save map for the character
	 *
	 * @return
	 */
	public Map<String, Integer> getDeathSave() {
		return this.deathSave;
	}

	/**
	 * Setter for the death save map for the character
	 *
	 * @param deathSave
	 */
	public void setDeathSave(Map<String, Integer> deathSave) {
		this.deathSave = deathSave;
	}

	/**
	 * Getter for the user's custom value map
	 *
	 * @return
	 */
	public Map<String, String> getUserSetValues() {
		return this.userSetValues;
	}

	/**
	 * Setter for the user's custom value map
	 *
	 * @param userSetValues
	 */
	public void setUserSetValues(Map<String, String> userSetValues) {
		this.userSetValues = userSetValues;
	}

	/**
	 * Getter for character's base armor class
	 *
	 * @return
	 */
	public int getBaseArmorClass() {
		return this.baseArmorClass;
	}

	/**
	 * Setter for character's base armor class
	 */
	public void setBaseArmorClass() {
		this.baseArmorClass = 10 + (this.dexterity != null ? this.dexterity.getAbilityModifier() : 0);
	}

	/**
	 * Getter for spell slots
	 *
	 * @return
	 */
	public List<SpellSlots> getSpellSlots() {
		return this.spellSlots;
	}

	/**
	 * Setter for spell slots
	 *
	 * @param spellSlots
	 */
	public void setSpellSlots(List<SpellSlots> spellSlots) {
		this.spellSlots = spellSlots;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return this.owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Helper method to create and prepare a blank character's death save map
	 *
	 * @return
	 */
	private Map<String, Integer> prepareDeathSaveMap() {
		LOG.info("preparing death save map");
		final Map<String, Integer> map = new HashMap<>();
		map.put(SUCCESSES, 0);
		map.put(FAILURES, 0);
		return map;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return "CharacterDto [name=" + this.name + ", race=" + this.race + ", level=" + this.level + ", baseArmorClass="
				+ this.baseArmorClass + ", maxHealth=" + this.maxHealth + ", currentHealth=" + this.currentHealth
				+ ", temporaryHitPoints=" + this.temporaryHitPoints + ", characterClass=" + this.characterClass
				+ ", spellSlots=" + this.spellSlots + ", isMulticlassed=" + this.isMulticlassed + ", active="
				+ this.active + ", strength=" + this.strength + ", dexterity=" + this.dexterity + ", constitution="
				+ this.constitution + ", intelligence=" + this.intelligence + ", wisdom=" + this.wisdom + ", charisma="
				+ this.charisma + ", chakra=" + this.chakra + ", deathSave=" + this.deathSave + ", userSetValues="
				+ this.userSetValues + "]";
	}

}
