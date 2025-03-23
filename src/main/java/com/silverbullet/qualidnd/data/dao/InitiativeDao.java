package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.GET_CURRENT_HEALTH_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.ORDER_INITIATIVE_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;
import static com.silverbullet.qualidnd.common.DDConstants.UPDATE_HEALTH_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.UPDATE_INIT_QUERY;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.InitiativeDto;

/**
 * DAO class that performs DB operations relating to Initiative and the
 * Initiative tracker
 * 
 * @author Batman
 *
 */
@Repository
public class InitiativeDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(InitiativeDao.class);

	/**
	 * Method to insert initiative rolls into the table
	 *
	 * @param init The initiative entity object to insert
	 * @return true if success, else false
	 */
	public boolean insertInitiativeRoll(InitiativeDto init) {
		LOG.info("Inserting roll into table");
		int success;
		try {
			success = this.template.update(DDConstants.INSERT_INIT_ROLL_QUERY, init.getUsername(), init.getTotalRoll(),
					init.getInitialRoll(), init.getModifier(), init.getCurrentHealth(), init.getCurrent());
			this.assessRolls();
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "insertInitiativeRoll: ", PROCESS_EXCEPTION, dae.getMessage()));
			return false;
		}
		return success >= 0;
	}

	/**
	 * Method to retrieve all initiative rolls
	 *
	 * @return List of all initiative rolls in the table
	 */
	public List<InitiativeDto> retrieveRolls() {
		LOG.info("Retrieving rolls");
		List<InitiativeDto> initList = new ArrayList<>();
		try {
			initList = this.template.query(DDConstants.GET_ALL_INIT_ROLL_QUERY,
					(result, rowNum) -> new InitiativeDto(result.getString(DDConstants.USERNAME_KEY),
							result.getInt(DDConstants.TOTAL_ROLL_KEY), result.getInt(DDConstants.INITIAL_ROLL_KEY),
							result.getInt(DDConstants.MODIFIER_KEY), result.getInt(DDConstants.CURRENT_HEALTH_KEY),
							result.getInt(DDConstants.CURRENT_KEY)));
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "retrieveRolls: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return initList;
	}

	/**
	 * Method to clear the initiative table
	 *
	 * @return true if successful, else false
	 */
	public boolean clearTable() {
		LOG.info("Clearing initiative table");
		try {
			this.template.update(DDConstants.CLEAR_INIT_TABLE_QUERY);
			return true;
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "clearTable: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return false;
	}

	public boolean removeCreature(String name) {
		LOG.info("Removing single init entry");
		try {
			this.template.update(DDConstants.REMOVE_SINGLE_ENTRY_QUERY, name);
			return true;
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "removeCreature: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return false;
	}

	/**
	 * Helper method to setup the correct current creature based on the values in
	 * the database at the time of insertion
	 */
	private void assessRolls() {
		List<InitiativeDto> list = new ArrayList<>();
		try {
			list = this.template.query(ORDER_INITIATIVE_QUERY,
					(result, rowNum) -> new InitiativeDto(result.getString(DDConstants.USERNAME_KEY),
							result.getInt(DDConstants.TOTAL_ROLL_KEY), result.getInt(DDConstants.INITIAL_ROLL_KEY),
							result.getInt(DDConstants.MODIFIER_KEY), result.getInt(DDConstants.CURRENT_HEALTH_KEY),
							result.getInt(DDConstants.CURRENT_KEY)));
			if (list.get(0).getCurrent() != 1 && list.size() > 1) {
				InitiativeDto old = new InitiativeDto();
				for (int i = 1; i < list.size(); i++) {
					if (list.get(i).getCurrent() == 1) {
						old = list.get(i);
						break;
					}
				}
				this.template.update(UPDATE_INIT_QUERY, list.get(0).getUsername(), list.get(0).getTotalRoll(),
						list.get(0).getInitialRoll(), list.get(0).getModifier(), list.get(0).getCurrentHealth(),
						list.get(0).getCurrent(), 1);
				this.template.update(UPDATE_INIT_QUERY, old.getUsername(), old.getTotalRoll(), old.getInitialRoll(),
						old.getModifier(), old.getCurrentHealth(), old.getCurrent(), 0);
			} else {
				this.template.update(UPDATE_INIT_QUERY, list.get(0).getUsername(), list.get(0).getTotalRoll(),
						list.get(0).getInitialRoll(), list.get(0).getModifier(), list.get(0).getCurrentHealth(),
						list.get(0).getCurrent(), 1);
			}
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "assessRolls: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
	}

	/**
	 * Method to advance the pointer in the DB to the next creature in the table
	 *
	 * @return True if success else false
	 */
	public boolean nextCreature() {
		LOG.info("Setting correct current");
		List<InitiativeDto> initList = new ArrayList<>();
		try {
			initList = this.template.query(DDConstants.ORDER_INITIATIVE_QUERY,
					(result, rowNum) -> new InitiativeDto(result.getString(DDConstants.USERNAME_KEY),
							result.getInt(DDConstants.TOTAL_ROLL_KEY), result.getInt(DDConstants.INITIAL_ROLL_KEY),
							result.getInt(DDConstants.MODIFIER_KEY), result.getInt(DDConstants.CURRENT_HEALTH_KEY),
							result.getInt(DDConstants.CURRENT_KEY)));
			InitiativeDto current = new InitiativeDto();
			InitiativeDto newCurrent = new InitiativeDto();
			int indexOfNew = 0;
			for (int i = 0; i < initList.size(); i++) {
				if (initList.get(i).getCurrent() == 1) {
					current = initList.get(i);
					indexOfNew = i + 1;
				}
				if (i == indexOfNew) {
					newCurrent = initList.get(i);
				}
			}
			if (StringUtils.isNotBlank(current.getUsername())) {
				this.template.update(UPDATE_INIT_QUERY, current.getUsername(), current.getTotalRoll(),
						current.getInitialRoll(), current.getModifier(), current.getCurrentHealth(),
						current.getCurrent(), 0);
				this.template.update(UPDATE_INIT_QUERY, newCurrent.getUsername(), newCurrent.getTotalRoll(),
						newCurrent.getInitialRoll(), newCurrent.getModifier(), newCurrent.getCurrentHealth(),
						newCurrent.getCurrent(), 1);
			} else {
				this.template.update(UPDATE_INIT_QUERY, newCurrent.getUsername(), newCurrent.getTotalRoll(),
						newCurrent.getInitialRoll(), newCurrent.getModifier(), newCurrent.getCurrentHealth(),
						newCurrent.getCurrent(), 1);
			}
			return true;
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "nextCreature: ", PROCESS_EXCEPTION, dae.getMessage()));
		}
		return false;
	}

	/**
	 * Method to adjust a creature's health in the initiative table
	 *
	 * @param name   The creature's name to adjust
	 * @param health The amount of health to reduce
	 * @return true if success, else false
	 */
	public boolean adjustHealth(String name, Integer health) {
		InitiativeDto creature;
		int success;
		try {
			creature = this.template.queryForObject(GET_CURRENT_HEALTH_QUERY, new Object[] { name },
					new BeanPropertyRowMapper<>(InitiativeDto.class));
			if (null != creature) {
				final Integer newHealth = creature.getCurrentHealth() - health;
				success = this.template.update(UPDATE_HEALTH_QUERY, newHealth, name);
			} else {
				success = -1;
			}
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "adjustHealth: ", PROCESS_EXCEPTION, dae.getMessage()));
			return false;
		}
		return success >= 0;
	}
}
