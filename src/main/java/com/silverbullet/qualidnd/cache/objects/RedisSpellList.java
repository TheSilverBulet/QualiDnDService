package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import com.silverbullet.qualidnd.data.dto.Spell;

/**
 * Class to encapsulate storing Spell Ability list into the Redis Cache.
 *
 * @author Batman
 *
 */
@RedisHash("Spells")
@Entity
public class RedisSpellList extends RedisObject implements Serializable {

	private static final long serialVersionUID = -6370210376766065152L;
	private List<Spell> spells;

	/**
	 * Constructor
	 */
	public RedisSpellList() {
		super();
		this.spells = new ArrayList<>();
	}

	/**
	 * Parameter constructor
	 *
	 * @param id     the id of the object to store
	 * @param spells the list of spells to store
	 */
	public RedisSpellList(final String id, final List<Spell> spells) {
		super(id);
		this.spells = spells;
	}

	/**
	 * Getter for the spell list
	 *
	 * @return the list of spells
	 */
	public List<Spell> getSpells() {
		return this.spells;
	}

	/**
	 * Setter for the spell list
	 *
	 * @param spells the list of spells to set
	 */
	public void setSpells(List<Spell> spells) {
		this.spells = spells;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return String.format("RedisSpellList [spells=%s, getId()=%s]", this.spells, this.getId());
	}

}
