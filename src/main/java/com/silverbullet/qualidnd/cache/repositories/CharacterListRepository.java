package com.silverbullet.qualidnd.cache.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.cache.objects.RedisCharacterList;
import com.silverbullet.qualidnd.data.dto.CharacterDto;

/**
 * Repository interface for performing basic CRUD operations on CharacterList
 * objects in the Redis Cache.
 *
 * @author Batman
 *
 */
@Repository
public interface CharacterListRepository extends CrudRepository<RedisCharacterList, String> {

	/**
	 * Helper method to cause less of a hassle when attempting to retrieve from
	 * Redis cache.
	 *
	 * @param id the id of the object to retrieve
	 * @return the object with the given id or null
	 */
	default List<CharacterDto> findByIdAsCharacterList(String id) {
		return this.findById(id).get().getCharacters();
	}

}
