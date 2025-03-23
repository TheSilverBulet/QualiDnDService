package com.silverbullet.qualidnd.data.dto;

import java.io.Serializable;

/**
 * Class to represent an Initiative record in the Initiative tracker
 *
 * @author Batman
 *
 */
public class InitiativeDto implements Serializable {

	private static final long serialVersionUID = -3103727572523991237L;
	private String username;
	private int totalRoll;
	private int initialRoll;
	private int modifier;
	private int currentHealth;
	private int current;
	private int miscBonus;
	private String rollType;

	/**
	 * No args constructor
	 */
	public InitiativeDto() {

	}

	public InitiativeDto(String username) {
		this.username = username;
	}

	/**
	 * Constructor for username and totalRoll only
	 *
	 * @param username  Username associated with the roll
	 * @param totalRoll Total Roll including initial roll and bonus
	 */
	public InitiativeDto(String username, int totalRoll) {
		this.username = username;
		this.totalRoll = totalRoll;
	}

	/**
	 * Full args constructor
	 *
	 * @param username      The username associated with the roll
	 * @param totalRoll     The total roll including initial roll and bonus
	 * @param initialRoll   The initial roll
	 * @param modifier      The creature's DEX modifier
	 * @param currentHealth The total health of the creature
	 * @param current       Whether or not it's the creatures turn in the initiative
	 *                      order
	 */
	public InitiativeDto(String username, int totalRoll, int initialRoll, int modifier, int currentHealth,
			int current) {
		this.username = username;
		this.totalRoll = totalRoll;
		this.initialRoll = initialRoll;
		this.modifier = modifier;
		this.currentHealth = currentHealth;
		this.current = current;
	}

	/**
	 * Getter for the username associated with the roll
	 *
	 * @return Username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter for the username associated with the roll
	 *
	 * @param username The username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for the total roll including the initial roll and bonus
	 *
	 * @return The total roll
	 */
	public int getTotalRoll() {
		return this.totalRoll;
	}

	/**
	 * Setter for the total roll
	 *
	 * @param totalRoll The total roll
	 */
	public void setTotalRoll(int totalRoll) {
		this.totalRoll = totalRoll;
	}

	/**
	 * Getter for the initial roll
	 *
	 * @return The initial roll
	 */
	public int getInitialRoll() {
		return this.initialRoll;
	}

	/**
	 * Setter for the initial roll
	 *
	 * @param initialRoll The initial roll
	 */
	public void setInitialRoll(int initialRoll) {
		this.initialRoll = initialRoll;
	}

	/**
	 * Getter for the DEX modifier bonus
	 *
	 * @return The modifier
	 */
	public int getModifier() {
		return this.modifier;
	}

	/**
	 * Setter for the DEX modifier bonus
	 *
	 * @param modifier The modifier
	 */
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	/**
	 * Getter for the max health for the creature
	 *
	 * @return The health
	 */
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	/**
	 * Setter for the max health
	 *
	 * @param currentHealth The health
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	/**
	 * Getter for the current flag
	 *
	 * @return 1 if current else 0
	 */
	public int getCurrent() {
		return this.current;
	}

	/**
	 * Setter for the current flag
	 *
	 * @param current 1 if current else 0
	 */
	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 * Getter for the misc bonus for the entity
	 *
	 * @return
	 */
	public int getMiscBonus() {
		return this.miscBonus;
	}

	/**
	 * Setter for the misc bonus for the entity
	 *
	 * @param miscBonus
	 */
	public void setMiscBonus(int miscBonus) {
		this.miscBonus = miscBonus;
	}

	/**
	 * Getter for the roll type for the entity
	 *
	 * @return
	 */
	public String getRollType() {
		return this.rollType;
	}

	/**
	 * Setter for the roll type for the entity
	 * 
	 * @param rollType
	 */
	public void setRollType(String rollType) {
		this.rollType = rollType;
	}

	@Override
	public String toString() {
		return "Initiative{" + "username='" + this.username + '\'' + ", totalRoll=" + this.totalRoll + ", initialRoll="
				+ this.initialRoll + ", modifier=" + this.modifier + ", currentHealth=" + this.currentHealth
				+ ", current=" + this.current + '}';
	}

}
