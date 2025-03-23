package com.silverbullet.qualidnd.data.dto;

import java.io.Serializable;

/**
 * Class to represent a Spell in D&D
 *
 * @author Batman
 *
 */
public class Spell implements Serializable {

	private static final long serialVersionUID = 6297212734482014257L;
	private String spellName;
	private String spellSchool;
	private Integer spellLevel;
	private String castingTime;
	private String range;
	private String components;
	private String duration;
	private String description;
	private Boolean isRitual;
	private Boolean bardCanCast;
	private Boolean bloodHunterCanCast;
	private Boolean clericCanCast;
	private Boolean druidCanCast;
	private Boolean paladinCanCast;
	private Boolean rangerCanCast;
	private Boolean sorcererCanCast;
	private Boolean warlockCanCast;
	private Boolean wizardCanCast;

	public Spell() {

	}

	/**
	 * @return the spellName
	 */
	public String getSpellName() {
		return this.spellName;
	}

	/**
	 * @param spellName the spellName to set
	 */
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	/**
	 * @return the spellSchool
	 */
	public String getSpellSchool() {
		return this.spellSchool;
	}

	/**
	 * @param spellSchool the spellSchool to set
	 */
	public void setSpellSchool(String spellSchool) {
		this.spellSchool = spellSchool;
	}

	/**
	 * @return the spellLevel
	 */
	public Integer getSpellLevel() {
		return this.spellLevel;
	}

	/**
	 * @param spellLevel the spellLevel to set
	 */
	public void setSpellLevel(Integer spellLevel) {
		this.spellLevel = spellLevel;
	}

	/**
	 * @return the castingTime
	 */
	public String getCastingTime() {
		return this.castingTime;
	}

	/**
	 * @param castingTime the castingTime to set
	 */
	public void setCastingTime(String castingTime) {
		this.castingTime = castingTime;
	}

	/**
	 * @return the range
	 */
	public String getRange() {
		return this.range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(String range) {
		this.range = range;
	}

	/**
	 * @return the components
	 */
	public String getComponents() {
		return this.components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(String components) {
		this.components = components;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return this.duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isRitual
	 */
	public Boolean getIsRitual() {
		return this.isRitual;
	}

	/**
	 * @param isRitual the isRitual to set
	 */
	public void setIsRitual(Boolean isRitual) {
		this.isRitual = isRitual;
	}

	/**
	 * @return the bardCanCast
	 */
	public Boolean getBardCanCast() {
		return this.bardCanCast;
	}

	/**
	 * @param bardCanCast the bardCanCast to set
	 */
	public void setBardCanCast(Boolean bardCanCast) {
		this.bardCanCast = bardCanCast;
	}

	/**
	 * @return the bloodHunterCanCast
	 */
	public Boolean getBloodHunterCanCast() {
		return this.bloodHunterCanCast;
	}

	/**
	 * @param bloodHunterCanCast the bloodHunterCanCast to set
	 */
	public void setBloodHunterCanCast(Boolean bloodHunterCanCast) {
		this.bloodHunterCanCast = bloodHunterCanCast;
	}

	/**
	 * @return the clericCanCast
	 */
	public Boolean getClericCanCast() {
		return this.clericCanCast;
	}

	/**
	 * @param clericCanCast the clericCanCast to set
	 */
	public void setClericCanCast(Boolean clericCanCast) {
		this.clericCanCast = clericCanCast;
	}

	/**
	 * @return the druidCanCast
	 */
	public Boolean getDruidCanCast() {
		return this.druidCanCast;
	}

	/**
	 * @param druidCanCast the druidCanCast to set
	 */
	public void setDruidCanCast(Boolean druidCanCast) {
		this.druidCanCast = druidCanCast;
	}

	/**
	 * @return the paladinCanCast
	 */
	public Boolean getPaladinCanCast() {
		return this.paladinCanCast;
	}

	/**
	 * @param paladinCanCast the paladinCanCast to set
	 */
	public void setPaladinCanCast(Boolean paladinCanCast) {
		this.paladinCanCast = paladinCanCast;
	}

	/**
	 * @return the rangerCanCast
	 */
	public Boolean getRangerCanCast() {
		return this.rangerCanCast;
	}

	/**
	 * @param rangerCanCast the rangerCanCast to set
	 */
	public void setRangerCanCast(Boolean rangerCanCast) {
		this.rangerCanCast = rangerCanCast;
	}

	/**
	 * @return the sorcererCanCast
	 */
	public Boolean getSorcererCanCast() {
		return this.sorcererCanCast;
	}

	/**
	 * @param sorcererCanCast the sorcererCanCast to set
	 */
	public void setSorcererCanCast(Boolean sorcererCanCast) {
		this.sorcererCanCast = sorcererCanCast;
	}

	/**
	 * @return the warlockCanCast
	 */
	public Boolean getWarlockCanCast() {
		return this.warlockCanCast;
	}

	/**
	 * @param warlockCanCast the warlockCanCast to set
	 */
	public void setWarlockCanCast(Boolean warlockCanCast) {
		this.warlockCanCast = warlockCanCast;
	}

	/**
	 * @return the wizardCanCast
	 */
	public Boolean getWizardCanCast() {
		return this.wizardCanCast;
	}

	/**
	 * @param wizardCanCast the wizardCanCast to set
	 */
	public void setWizardCanCast(Boolean wizardCanCast) {
		this.wizardCanCast = wizardCanCast;
	}

	@Override
	public String toString() {
		return "Spell [spellName=" + this.spellName + ", spellSchool=" + this.spellSchool + ", spellLevel="
				+ this.spellLevel + ", castingTime=" + this.castingTime + ", range=" + this.range + ", components="
				+ this.components + ", duration=" + this.duration + ", description=" + this.description + ", isRitual="
				+ this.isRitual + ", bardCanCast=" + this.bardCanCast + ", bloodHunterCanCast="
				+ this.bloodHunterCanCast + ", clericCanCast=" + this.clericCanCast + ", druidCanCast="
				+ this.druidCanCast + ", paladinCanCast=" + this.paladinCanCast + ", rangerCanCast="
				+ this.rangerCanCast + ", sorcererCanCast=" + this.sorcererCanCast + ", warlockCanCast="
				+ this.warlockCanCast + ", wizardCanCast=" + this.wizardCanCast + "]";
	}

}
