package com.silverbullet.qualidnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.silverbullet.qualidnd.service.AbilityService;
import com.silverbullet.qualidnd.service.CharacterService;
import com.silverbullet.qualidnd.service.MonsterService;
import com.silverbullet.qualidnd.service.RedisService;

/**
 * Initializer class that automatically runs after app has started. Ensures that
 * cached data is present in the Redis Cache.
 *
 * @author Batman
 *
 */
@Component
public class ApplicationCacheInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private static final boolean FORCE = true;
	private static final boolean SPELL = true;
	private static final boolean JUTSU = false;

	@Autowired
	private AbilityService abilityService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MonsterService monsterService;
	@Autowired
	private CharacterService characterService;

	/**
	 * Method that is called on Application start
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		this.refreshCache();
	}

	/**
	 * Driver method for refreshing the cache.
	 */
	private void refreshCache() {
		this.refreshAbilities();
		this.refreshMonsterList();
		this.refreshCharacterList();
	}

	/**
	 * Driver method to ensure that the ability lists are refreshed
	 */
	private void refreshAbilities() {
		this.redisService.putAbilityList(this.abilityService.getAbilities(SPELL), SPELL, FORCE);
		this.redisService.putAbilityList(this.abilityService.getAbilities(JUTSU), JUTSU, FORCE);
	}

	/**
	 * Driver method to ensure that the monster list is refreshed
	 */
	private void refreshMonsterList() {
		this.redisService.putMonsterList(this.monsterService.getMonsterList(), FORCE);
	}

	/**
	 *
	 */
	private void refreshCharacterList() {
		this.redisService.putCharacterList(this.characterService.fetchAllCharacters(), FORCE);
	}
}
