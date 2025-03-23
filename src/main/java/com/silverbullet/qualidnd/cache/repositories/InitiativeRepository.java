package com.silverbullet.qualidnd.cache.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.cache.objects.RedisInitiative;
import com.silverbullet.qualidnd.data.dto.InitiativeDto;

/**
 * Repository interface for performing basic CRUD operations on Initiative
 * objects in the Redis Cache.
 *
 * @author Batman
 *
 */
@Repository
public interface InitiativeRepository extends CrudRepository<RedisInitiative, String> {

	/**
	 * Helper method to cause less of a hassle when attempting to retrieve from
	 * Redis cache.
	 * 
	 * @param id the id of the object to retrieve
	 * @return the object with the given id or null
	 */
	default InitiativeDto findByIdAsInitiativeDto(String id) {
		return this.findById(id).get().getDto();
	}

}
