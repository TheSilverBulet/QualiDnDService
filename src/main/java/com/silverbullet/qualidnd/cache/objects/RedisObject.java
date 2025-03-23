package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * Base Cache Object class. All other Redis objects should extend this class.
 *
 * @author Batman
 *
 */
public class RedisObject implements Serializable {

	private static final long serialVersionUID = 2595782264486002049L;
	private String id = StringUtils.EMPTY;

	/**
	 * Constructor
	 */
	public RedisObject() {

	}

	/**
	 * Parameter constructor
	 *
	 * @param id the id of the object to store
	 */
	public RedisObject(final String id) {
		this.id = id;
	}

	/**
	 * Getter for object Id
	 *
	 * @return the object id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter for the object id
	 *
	 * @param id the object id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return String.format("RedisObject [id=%s]", this.id);
	}

}
