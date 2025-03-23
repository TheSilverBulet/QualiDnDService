package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import com.silverbullet.qualidnd.data.dto.InitiativeDto;

/**
 * Class to encapsulate storing initiatives into the Redis Cache.
 *
 * @author Batman
 *
 */
@RedisHash("Initiative")
@Entity
public class RedisInitiative extends RedisObject implements Serializable {

	private static final long serialVersionUID = 2858921438502816731L;
	private InitiativeDto dto;

	/**
	 * Constructor
	 */
	public RedisInitiative() {
		super();
		this.dto = new InitiativeDto();
	}

	/**
	 * Parameter constructor
	 *
	 * @param id  id of the object to store
	 * @param dto the dto to store
	 */
	public RedisInitiative(final String id, final InitiativeDto dto) {
		super(id);
		this.dto = dto;
	}

	/**
	 * Getter for the dto
	 *
	 * @return initiativeDto that was stored in the cache as a part of the
	 *         RedisInitiative
	 */
	public InitiativeDto getDto() {
		return this.dto;
	}

	/**
	 * Setter for the dto
	 *
	 * @param dto
	 */
	public void setDto(InitiativeDto dto) {
		this.dto = dto;
	}

	/**
	 * toString() method for the class, including the getId() from the super class
	 */
	@Override
	public String toString() {
		return String.format("RedisInitiative [dto=%s, getId()=%s]", this.dto, this.getId());
	}

}
