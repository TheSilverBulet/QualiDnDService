package com.silverbullet.qualidnd.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.data.dao.AbilityDao;
import com.silverbullet.qualidnd.data.dto.Jutsu;
import com.silverbullet.qualidnd.data.dto.Spell;

/**
 * Service class to be the middleman between controllers and the AbilityDao
 *
 * @author Batman
 *
 */
@Service
public class AbilityService implements Serializable {

	private static final long serialVersionUID = 2886129120334322630L;
	@Autowired
	private AbilityDao abilityDao;

	public List<?> getAbilities(final boolean spells) {
		if (spells) {
			return this.abilityDao.getAllSpells();
		} else {
			return this.abilityDao.getAllJutsu();
		}
	}

	public boolean saveAbility(final Object ability, boolean spell) {
		if (spell) {
			return this.abilityDao.saveSpell((Spell) ability);
		} else {
			return this.abilityDao.saveJutsu((Jutsu) ability);
		}
	}

}
