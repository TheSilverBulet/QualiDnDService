package com.silverbullet.qualidnd.data.dto;

/**
 * Class to represent a spell slot in D&D
 *
 * @author Batman
 *
 */
public class SpellSlots {

	private Integer slotLevel = 0;
	private Integer numberOfSlots = 0;

	/**
	 * @return the slotLevel
	 */
	public Integer getSlotLevel() {
		return this.slotLevel;
	}

	/**
	 * @param slotLevel the slotLevel to set
	 */
	public void setSlotLevel(Integer slotLevel) {
		this.slotLevel = slotLevel;
	}

	/**
	 * @return the numberOfSlots
	 */
	public Integer getNumberOfSlots() {
		return this.numberOfSlots;
	}

	/**
	 * @param numberOfSlots the numberOfSlots to set
	 */
	public void setNumberOfSlots(Integer numberOfSlots) {
		this.numberOfSlots = numberOfSlots;
	}

	@Override
	public String toString() {
		return String.format("SpellSlots [slotLevel=%s, numberOfSlots=%s]", this.slotLevel, this.numberOfSlots);
	}

}
