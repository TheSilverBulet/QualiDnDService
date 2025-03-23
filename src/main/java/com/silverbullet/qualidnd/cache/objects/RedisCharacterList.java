package com.silverbullet.qualidnd.cache.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import com.silverbullet.qualidnd.data.dto.CharacterDto;

/**
 * Class to encapsulate storing Characters into the Redis Cache.
 *
 * @author Batman
 *
 */
@RedisHash("CharacterList")
@Entity
public class RedisCharacterList extends RedisObject implements Serializable {

	private static final long serialVersionUID = 2914938544424251151L;
	List<CharacterDto> characters;

	public RedisCharacterList() {
		super();
		this.characters = new ArrayList<>();
	}

	public RedisCharacterList(final String id, final List<CharacterDto> characters) {
		super(id);
		this.characters = characters;
	}

	/**
	 * @return the characters
	 */
	public List<CharacterDto> getCharacters() {
		return this.characters;
	}

	/**
	 * @param characters the characters to set
	 */
	public void setCharacters(List<CharacterDto> characters) {
		this.characters = characters;
	}

	@Override
	public String toString() {
		return String.format("RedisCharacterList [characters=%s, getId()=%s]", this.characters, this.getId());
	}

}
