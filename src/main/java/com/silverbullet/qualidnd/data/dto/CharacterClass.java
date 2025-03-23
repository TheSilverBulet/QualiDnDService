package com.silverbullet.qualidnd.data.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * Class representation of a D&D class where the class name and level are stored
 *
 * @author Batman
 *
 */
public class CharacterClass {
	private String className = StringUtils.EMPTY;
	private int classLevel = 0;

	/**
	 * Constructor
	 */
	public CharacterClass() {
	}

	/**
	 * Parameter Constructor
	 *
	 * @param className  the name of the class (should be from the class master
	 *                   list)
	 * @param classLevel the level of the class (should not exceed 20)
	 */
	public CharacterClass(String className, int classLevel) {
		this.className = className;
		this.classLevel = classLevel > 20 ? 20 : classLevel;
	}

	/**
	 * Getter for className
	 *
	 * @return the className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * Setter for className
	 *
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Getter for classLevel
	 *
	 * @return the classLevel
	 */
	public int getClassLevel() {
		return this.classLevel;
	}

	/**
	 * Setter for classLevel
	 *
	 * @param classLevel the classLevel to set (should not exceed 20)
	 */
	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel > 20 ? 20 : classLevel;
	}

	/**
	 * toString method for the class
	 */
	@Override
	public String toString() {
		return "CharacterClass [className=" + this.className + ", classLevel=" + this.classLevel + "]";
	}

}