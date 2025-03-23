package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import com.silverbullet.qualidnd.data.dto.Jutsu;

/**
 * Class to encapsulate storing Jutsu Ability list into the Redis Cache.
 *
 * @author Batman
 *
 */
@RedisHash("Jutsu")
@Entity
public class RedisJutsuList extends RedisObject implements Serializable {

	private static final long serialVersionUID = 4737678721995879657L;
	private List<Jutsu> jutsu;

	/**
	 * Constructor
	 */
	public RedisJutsuList() {
		super();
		this.jutsu = new ArrayList<>();
	}

	/**
	 * Parameter constructor
	 *
	 * @param id    the id of the object to store
	 * @param jutsu the jutsu list to store
	 */
	public RedisJutsuList(final String id, final List<Jutsu> jutsu) {
		super(id);
		this.jutsu = jutsu;
	}

	/**
	 * Getter for the jutsu list
	 *
	 * @return the jutsu list
	 */
	public List<Jutsu> getJutsu() {
		return this.jutsu;
	}

	/**
	 * Setter for the jutsu list
	 *
	 * @param jutsu the jutsu list to store
	 */
	public void setJutsu(List<Jutsu> jutsu) {
		this.jutsu = jutsu;
	}

	/**
	 * toString() method for the class
	 */
	@Override
	public String toString() {
		return String.format("RedisJutsuList [jutsu=%s, getId()=%s]", this.jutsu, this.getId());
	}

}
