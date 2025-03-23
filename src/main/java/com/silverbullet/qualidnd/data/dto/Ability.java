package com.silverbullet.qualidnd.data.dto;

import com.silverbullet.qualidnd.util.CreatureUtils;

/**
 * Class to represent an Ability Score as if it were written on the DnD
 * character sheet (STR, DEX, CON, etc.)
 *
 * @author Batman
 *
 */
public class Ability {

	// Score always means the larger number (between 0-30)
	private Integer abilityScore;
	// Modifier always means the +/- number used to apply to rolls (between -5 & +5)
	private Integer abilityModifier;

	/**
	 * Constructor
	 */
	public Ability() {
	}

	/**
	 * Parameter Constructor
	 *
	 * @param score the score of the ability as an int
	 */
	public Ability(int score) {
		this.abilityScore = score;
		this.abilityModifier = CreatureUtils.getModifier(this.abilityScore);
	}

	/**
	 * Parameter Constructor
	 *
	 * @param score the score of the ability as a String
	 */
	public Ability(String score) {
		this.abilityScore = Integer.parseInt(score);
		this.abilityModifier = CreatureUtils.getModifier(this.abilityScore);
	}

	/**
	 * Getter for abilityScore
	 *
	 * @return the score of the ability
	 */
	public int getAbilityScore() {
		return this.abilityScore;
	}

	/**
	 * Setter for abilityScore
	 *
	 * @param abilityScore the ability score to set
	 */
	public void setAbilityScore(int abilityScore) {
		this.abilityScore = abilityScore;
	}

	/**
	 * Getter for ability modifier
	 *
	 * @return ability modifier for this ability
	 */
	public int getAbilityModifier() {
		return this.abilityModifier;
	}

	/**
	 * Setter for the abilityModifier
	 *
	 * @param abilityModifier the abilityModifier to set
	 */
	public void setAbilityModifier(int abilityModifier) {
		this.abilityModifier = abilityModifier;
	}

	/**
	 * Variant for Setter for abilityModifier that relies on using the ability score
	 * to calculate the modifier
	 */
	public void setAbilityModifier() {
		this.abilityModifier = CreatureUtils.getModifier(this.abilityScore);
	}

	/**
	 * toString() method for this class
	 */
	@Override
	public String toString() {
		return "Ability [abilityScore=" + this.abilityScore + ", abilityModifier=" + this.abilityModifier + "]";
	}

}
