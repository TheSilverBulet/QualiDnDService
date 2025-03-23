package com.silverbullet.qualidnd.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.data.dao.MonsterDao;
import com.silverbullet.qualidnd.data.dto.Monster;

/**
 * Service class to act as middleman between controllers and the MonsterDao
 *
 * @author Batman
 *
 */
@Service
public class MonsterService implements Serializable {

	private static final long serialVersionUID = -7940336137648424056L;
	@Autowired
	private MonsterDao monsterDao;

	/**
	 * Method to retrieve all monsters
	 *
	 * @return the complete list of Monsters
	 */
	public List<Monster> getMonsterList() {
		return this.monsterDao.getAllMonsters();
	}

	/**
	 * Method to save a monster to the DB
	 * 
	 * @param mon the monster object to save to the DB
	 * @return true if success, else false
	 */
	public boolean saveMonster(Monster mon) {
		return this.monsterDao.saveMonster(mon);
	}

}
