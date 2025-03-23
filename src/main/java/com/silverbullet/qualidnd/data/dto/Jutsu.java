package com.silverbullet.qualidnd.data.dto;

import java.io.Serializable;

/**
 * Class to represent a Jutsu object
 *
 * @author Batman
 *
 */
public class Jutsu implements Serializable {

	private static final long serialVersionUID = -688415625909375141L;
	private String jutsuName;
	private String release;
	private String rank;
	private Integer cost;
	private String range;
	private String description;
	private String duration;

	/**
	 * Constructor
	 */
	public Jutsu() {
	}

	/**
	 * Getter for Jutsu name
	 *
	 * @return
	 */
	public String getJutsuName() {
		return this.jutsuName;
	}

	/**
	 * Setter for Jutsu name
	 *
	 * @param jutsuName
	 */
	public void setJutsuName(String jutsuName) {
		this.jutsuName = jutsuName;
	}

	/**
	 * Getter for release type
	 *
	 * @return
	 */
	public String getRelease() {
		return this.release;
	}

	/**
	 * Setter for release type
	 *
	 * @param release
	 */
	public void setRelease(String release) {
		this.release = release;
	}

	/**
	 * Getter for the Jutsu rank (representation of the general strength of a jutsu)
	 *
	 * @return
	 */
	public String getRank() {
		return this.rank;
	}

	/**
	 * Setter for the Jutsu rank
	 *
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}

	/**
	 * Getter for the cost of the Jutsu (in Chakra)
	 *
	 * @return
	 */
	public Integer getCost() {
		return this.cost;
	}

	/**
	 * Setter for the cost of the Jutsu
	 *
	 * @param cost
	 */
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	/**
	 * Getter for The range that the Jutsu extends through
	 *
	 * @return
	 */
	public String getRange() {
		return this.range;
	}

	/**
	 * Setter for the range of the chakra
	 *
	 * @param range
	 */
	public void setRange(String range) {
		this.range = range;
	}

	/**
	 * Getter for the description of the Jutsu
	 *
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter for the description of the Jutsu
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for the duration of the Jutsu
	 *
	 * @return
	 */
	public String getDuration() {
		return this.duration;
	}

	/**
	 * Setter for the duration of the Jutsu
	 *
	 * @param duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return String.format(
				"Jutsu [jutsuName=%s, release=%s, rank=%s, cost=%s, range=%s, description=%s, duration=%s]",
				this.jutsuName, this.release, this.rank, this.cost, this.range, this.description, this.duration);
	}

}
