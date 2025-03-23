package com.silverbullet.qualidnd.controller;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.data.dao.FileDao;
import com.silverbullet.qualidnd.data.dao.UserDao;
import com.silverbullet.qualidnd.security.CustomAuthProvider;
import com.silverbullet.qualidnd.service.AbilityService;
import com.silverbullet.qualidnd.service.CharacterService;
import com.silverbullet.qualidnd.service.InitiativeService;
import com.silverbullet.qualidnd.service.MonsterService;
import com.silverbullet.qualidnd.service.QDDUserService;
import com.silverbullet.qualidnd.service.RedisService;
import com.silverbullet.qualidnd.user.QDDUser;

/**
 * The Base class that all Controller will extend
 *
 * @author Batman
 *
 */
@QDDController
public class BaseController {

	private static final Logger LOG = LogManager.getLogger(BaseController.class);
	protected Random rand = new SecureRandom();

	/**
	 * Misc Class Variables
	 */
	protected static final String[] referenceFileList = new String[] { "Backgrounds", "Barbarian", "Bard",
			"Blood_Hunter", "Cantrips", "Cleric", "Druid", "Fairy", "Fighter", "Gunslinger", "Level_1", "Level_2",
			"Level_3", "Level_4", "Level_5", "Level_6", "Level_7", "Level_8", "Level_9", "Monk", "Paladin", "Races",
			"Ranger", "Rogue", "Shinobi", "Sorcerer", "Warlock", "Witcher", "Wizard", "TinyPlayerReference",
			"Turn_Cheat_Sheet" };

	protected static final String[] classList = new String[] { "Barbarian", "Bard", "Blood Hunter", "Cleric", "Druid",
			"Fighter", "Gunslinger", "Monk", "Paladin", "Ranger", "Rogue", "Shinobi", "Sorcerer", "Warlock", "Witcher",
			"Wizard" };

	/**
	 * Data Access Objects
	 */
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected AbilityService abilityService;
	@Autowired
	protected InitiativeService initService;
	@Autowired
	protected CharacterService characterService;
	@Autowired
	protected FileDao fileDao;
	@Autowired
	protected MonsterService monsterService;
	@Autowired
	protected QDDUserService userService;
	@Autowired
	protected CustomAuthProvider authManager;
	@Autowired
	protected RedisService redisService;

	BaseController() {

	}

	/**
	 * Helper method to roll initiative for a player
	 *
	 * @return The result of the roll
	 */
	protected int getInitiative() {
		LOG.info("Rolling Initiative");
		return this.rand.nextInt(20) + 1;
	}

	/**
	 * Helper method to roll initiative for a player with advantage
	 *
	 * @return The result of the roll
	 */
	protected int getInitiativeWAdvantage() {
		LOG.info("Rolling Initiative with Advantage");
		final int roll1 = this.rand.nextInt(20) + 1;
		final int roll2 = this.rand.nextInt(20) + 1;
		return roll1 > roll2 ? roll1 : roll2;
	}

	/**
	 * Helper method to roll initiative for a player with disadvantage
	 *
	 * @return The result of the roll
	 */
	protected int getInitiativeWDisadvantage() {
		LOG.info("Rolling Initiative with Disadvantage");
		final int roll1 = this.rand.nextInt(20) + 1;
		final int roll2 = this.rand.nextInt(20) + 1;
		return roll1 < roll2 ? roll1 : roll2;
	}

	/**
	 * Simple credential check to make sure the user is actually logged in
	 *
	 * @param activeUser the user to check against
	 */
	protected void checkCredentials(QDDUser activeUser) {
		if (activeUser == null || activeUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			throw new AccessDeniedException("User is not logged in");
		}
	}
}
