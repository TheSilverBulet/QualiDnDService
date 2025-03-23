package com.silverbullet.qualidnd.data.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * Class representing a D&D Monster
 *
 * @author Batman
 *
 */
public class Monster implements ICreature {

	String commonName = StringUtils.EMPTY;
	String type = StringUtils.EMPTY;
	String subType = StringUtils.EMPTY;
	Integer armorClass = 10;
	Integer health = 0;
	String avgHitDice = StringUtils.EMPTY;
	Ability strength = null;
	Ability dexterity = null;
	Ability constitution = null;
	Ability intelligence = null;
	Ability wisdom = null;
	Ability charisma = null;
	String speed = StringUtils.EMPTY;
	String skills = StringUtils.EMPTY;
	String savingThrows = StringUtils.EMPTY;
	String damageResistances = StringUtils.EMPTY;
	String damageImmunities = StringUtils.EMPTY;
	String damageVulnerabilities = StringUtils.EMPTY;
	String senses = StringUtils.EMPTY;
	String languages = StringUtils.EMPTY;
	Double challengeRating = 0.0;
	Integer expValue = 0;
	String abilities = StringUtils.EMPTY;
	String attacks = StringUtils.EMPTY;
	String size = StringUtils.EMPTY;
	String alignment = StringUtils.EMPTY;
	String conditionImmunities = StringUtils.EMPTY;
	String legendaryActions = StringUtils.EMPTY;

	/**
	 * Constructor
	 */
	public Monster() {
	}

	/**
	 * @return the commonName
	 */
	public String getCommonName() {
		return this.commonName;
	}

	/**
	 * @param commonName the commonName to set
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the subType
	 */
	public String getSubType() {
		return this.subType;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}

	/**
	 * @return the armorClass
	 */
	public Integer getArmorClass() {
		return this.armorClass;
	}

	/**
	 * @param armorClass the armorClass to set
	 */
	public void setArmorClass(Integer armorClass) {
		this.armorClass = armorClass;
	}

	/**
	 * @return the health
	 */
	public Integer getHealth() {
		return this.health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(Integer health) {
		this.health = health;
	}

	/**
	 * @return the avgHitDice
	 */
	public String getAvgHitDice() {
		return this.avgHitDice;
	}

	/**
	 * @param avgHitDice the avgHitDice to set
	 */
	public void setAvgHitDice(String avgHitDice) {
		this.avgHitDice = avgHitDice;
	}

	/**
	 * @return the strength
	 */
	@Override
	public Ability getStrength() {
		return this.strength;
	}

	/**
	 * @param strength the strength to set
	 */
	@Override
	public void setStrength(Ability strength) {
		this.strength = strength;
	}

	/**
	 * @return the dexterity
	 */
	@Override
	public Ability getDexterity() {
		return this.dexterity;
	}

	/**
	 * @param dexterity the dexterity to set
	 */
	@Override
	public void setDexterity(Ability dexterity) {
		this.dexterity = dexterity;
	}

	/**
	 * @return the constitution
	 */
	@Override
	public Ability getConstitution() {
		return this.constitution;
	}

	/**
	 * @param constitution the constitution to set
	 */
	@Override
	public void setConstitution(Ability constitution) {
		this.constitution = constitution;
	}

	/**
	 * @return the intelligence
	 */
	@Override
	public Ability getIntelligence() {
		return this.intelligence;
	}

	/**
	 * @param intelligence the intelligence to set
	 */
	@Override
	public void setIntelligence(Ability intelligence) {
		this.intelligence = intelligence;
	}

	/**
	 * @return the wisdom
	 */
	@Override
	public Ability getWisdom() {
		return this.wisdom;
	}

	/**
	 * @param wisdom the wisdom to set
	 */
	@Override
	public void setWisdom(Ability wisdom) {
		this.wisdom = wisdom;
	}

	/**
	 * @return the charisma
	 */
	@Override
	public Ability getCharisma() {
		return this.charisma;
	}

	/**
	 * @param charisma the charisma to set
	 */
	@Override
	public void setCharisma(Ability charisma) {
		this.charisma = charisma;
	}

	/**
	 * @return the speed
	 */
	public String getSpeed() {
		return this.speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * @return the skills
	 */
	public String getSkills() {
		return this.skills;
	}

	/**
	 * @param skills the skills to set
	 */
	public void setSkills(String skills) {
		this.skills = skills;
	}

	/**
	 * @return the savingThrows
	 */
	public String getSavingThrows() {
		return this.savingThrows;
	}

	/**
	 * @param savingThrows the savingThrows to set
	 */
	public void setSavingThrows(String savingThrows) {
		this.savingThrows = savingThrows;
	}

	/**
	 * @return the damageResistances
	 */
	public String getDamageResistances() {
		return this.damageResistances;
	}

	/**
	 * @param damageResistances the damageResistances to set
	 */
	public void setDamageResistances(String damageResistances) {
		this.damageResistances = damageResistances;
	}

	/**
	 * @return the damageImmunities
	 */
	public String getDamageImmunities() {
		return this.damageImmunities;
	}

	/**
	 * @param damageImmunities the damageImmunities to set
	 */
	public void setDamageImmunities(String damageImmunities) {
		this.damageImmunities = damageImmunities;
	}

	/**
	 * @return the damageVulnerabilities
	 */
	public String getDamageVulnerabilities() {
		return this.damageVulnerabilities;
	}

	/**
	 * @param damageVulnerabilities the damageVulnerabilities to set
	 */
	public void setDamageVulnerabilities(String damageVulnerabilities) {
		this.damageVulnerabilities = damageVulnerabilities;
	}

	/**
	 * @return the senses
	 */
	public String getSenses() {
		return this.senses;
	}

	/**
	 * @param senses the senses to set
	 */
	public void setSenses(String senses) {
		this.senses = senses;
	}

	/**
	 * @return the languages
	 */
	public String getLanguages() {
		return this.languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(String languages) {
		this.languages = languages;
	}

	/**
	 * @return the challengeRating
	 */
	public Double getChallengeRating() {
		return this.challengeRating;
	}

	/**
	 * @param challengeRating the challengeRating to set
	 */
	public void setChallengeRating(Double challengeRating) {
		this.challengeRating = challengeRating;
	}

	/**
	 * @return the expValue
	 */
	public Integer getExpValue() {
		return this.expValue;
	}

	/**
	 * @param expValue the expValue to set
	 */
	public void setExpValue(Integer expValue) {
		this.expValue = expValue;
	}

	/**
	 * @return the abilities
	 */
	public String getAbilities() {
		return this.abilities;
	}

	/**
	 * @param abilities the abilities to set
	 */
	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}

	/**
	 * @return the attacks
	 */
	public String getAttacks() {
		return this.attacks;
	}

	/**
	 * @param attacks the attacks to set
	 */
	public void setAttacks(String attacks) {
		this.attacks = attacks;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return this.size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the alignment
	 */
	public String getAlignment() {
		return this.alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the conditionImmunities
	 */
	public String getConditionImmunities() {
		return this.conditionImmunities;
	}

	/**
	 * @param conditionImmunities the conditionImmunities to set
	 */
	public void setConditionImmunities(String conditionImmunities) {
		this.conditionImmunities = conditionImmunities;
	}

	/**
	 * @return the legendaryActions
	 */
	public String getLegendaryActions() {
		return this.legendaryActions;
	}

	/**
	 * @param legendaryActions the legendaryActions to set
	 */
	public void setLegendaryActions(String legendaryActions) {
		this.legendaryActions = legendaryActions;
	}

	@Override
	public String toString() {
		return String.format(
				"Monster [commonName=%s, type=%s, subType=%s, armorClass=%s, health=%s, avgHitDice=%s, strength=%s, dexterity=%s, constitution=%s, intelligence=%s, wisdom=%s, charisma=%s, speed=%s, skills=%s, savingThrows=%s, damageResistances=%s, damageImmunities=%s, damageVulnerabilities=%s, senses=%s, languages=%s, challengeRating=%s, expValue=%s, abilities=%s, attacks=%s, size=%s, alignment=%s, conditionImmunities=%s, legendaryActions=%s]",
				this.commonName, this.type, this.subType, this.armorClass, this.health, this.avgHitDice, this.strength,
				this.dexterity, this.constitution, this.intelligence, this.wisdom, this.charisma, this.speed,
				this.skills, this.savingThrows, this.damageResistances, this.damageImmunities,
				this.damageVulnerabilities, this.senses, this.languages, this.challengeRating, this.expValue,
				this.abilities, this.attacks, this.size, this.alignment, this.conditionImmunities,
				this.legendaryActions);
	}

}
