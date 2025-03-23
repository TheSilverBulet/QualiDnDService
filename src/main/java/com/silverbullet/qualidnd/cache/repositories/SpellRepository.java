package com.silverbullet.qualidnd.cache.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.cache.objects.RedisSpellList;
import com.silverbullet.qualidnd.data.dto.Spell;

/**
 * Repository interface for performing basic CRUD operations on SpellList
 * objects in the Redis Cache.
 *
 * @author Batman
 *
 */
@Repository
public interface SpellRepository extends CrudRepository<RedisSpellList, String> {

	/**
	 * Helper method to cause less of a hassle when attempting to retrieve from
	 * Redis cache.
	 *
	 * @param id the id of the object to retrieve
	 * @return the object with the given id or null
	 */
	default List<Spell> findByIdAsSpell(String id) {
		return this.findById(id).get().getSpells();
	}
}
