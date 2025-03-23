package com.silverbullet.qualidnd.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.silverbullet.qualidnd.data.dao.InitiativeDao;
import com.silverbullet.qualidnd.data.dto.InitiativeDto;

/**
 * Service class to act as a middleman between the controllers and the
 * InitiativeDao
 * 
 * @author Batman
 *
 */
@Service
public class InitiativeService implements Serializable {

	private static final long serialVersionUID = 4819171784581064049L;
	@Autowired
	private InitiativeDao initDao;

	/**
	 * Method to perform the insert into the Initiative table
	 *
	 * @param dto the object containing the roll info
	 * @return The Response as a success or fail of the operation
	 */
	public boolean insertInitiative(final InitiativeDto dto) {
		return this.initDao.insertInitiativeRoll(dto);
	}

	/**
	 * Method to advance the pointer in the DB
	 *
	 * @return Value of the Response: SUCCESS or FAIL
	 */
	public boolean advanceCursor() {
		return this.initDao.nextCreature();
	}

	/**
	 * Method to clear the initiative Table in the DB
	 *
	 * @return true if success, else false
	 */
	public boolean purgeTable() {
		return this.initDao.clearTable();
	}

	/**
	 * Method to allow the admin to adjust the health of a creature in the init
	 * table
	 *
	 * @param name   the name of the entity
	 * @param health the amount of health to change
	 * @return true if success, else false
	 */
	public boolean adjustEntityHealth(final String name, final Integer health) {
		return this.initDao.adjustHealth(name, health);
	}

	/**
	 * Method to remove an entity from the init table in the DB
	 *
	 * @param name the name of the entity to remove
	 * @return true if success, else false
	 */
	public boolean removeEntity(final String name) {
		return this.initDao.removeCreature(name);
	}

	/**
	 * Method to retrieve all rolls in the init table from the DB
	 *
	 * @return the list of all rolls in the DB
	 */
	public List<InitiativeDto> fetchTable() {
		return this.initDao.retrieveRolls();
	}
}
