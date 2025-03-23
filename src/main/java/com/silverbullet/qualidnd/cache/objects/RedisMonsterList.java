package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import com.silverbullet.qualidnd.data.dto.Monster;

/**
 * Class to encapsulate storing Monster list into the Redis Cache.
 *
 * @author Batman
 *
 */
@RedisHash("Monster")
@Entity
public class RedisMonsterList extends RedisObject implements Serializable {

	private static final long serialVersionUID = -8333047688418258633L;
	private List<Monster> monsters;

	/**
	 * Constructor
	 */
	public RedisMonsterList() {
		super();
		this.monsters = new ArrayList<>();
	}

	/**
	 * Param Constructor
	 *
	 * @param id       id of the object to store
	 * @param monsters list of monsters to store
	 */
	public RedisMonsterList(final String id, final List<Monster> monsters) {
		super(id);
		this.monsters = monsters;
	}

	/**
	 * Getter for the Monster List
	 *
	 * @return the monster list
	 */
	public List<Monster> getMonsters() {
		return this.monsters;
	}

	/**
	 * Setter for the Monster list
	 *
	 * @param monsters the monster list to store
	 */
	public void setMonsters(List<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return String.format("RedisMonsterList [monsters=%s, getId()=%s]", this.monsters, this.getId());
	}

}
