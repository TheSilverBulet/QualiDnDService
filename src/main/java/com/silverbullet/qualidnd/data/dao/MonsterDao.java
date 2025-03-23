package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.Monster;
import com.silverbullet.qualidnd.util.CreatureUtils;
import com.silverbullet.qualidnd.util.SQLUtils;

/**
 * DAO class to perform DB operations relating to Monsters and the Monster List
 *
 * @author Batman
 *
 */
@Repository
public class MonsterDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(MonsterDao.class);

	/**
	 * Method to retrieve the full set of Monsters from the DB
	 *
	 * @return the full set of Monsters as a list
	 */
	public List<Monster> getAllMonsters() {
		List<Monster> monsters = new ArrayList<>();

		try {
			final List<Map<String, Object>> rows = this.template.queryForList(DDConstants.RETRIEVE_ALL_MONS_QUERY);
			monsters = this.transformRawMonsterOutputToList(rows);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "getAllMonsters: ", PROCESS_EXCEPTION, dae.getMessage()));
		}

		return monsters;
	}

	/**
	 * Method to save a new Monster to the DB
	 *
	 * @param newMonster the new Monster to save to the DB
	 * @return true if success, else false
	 */
	public boolean saveMonster(Monster newMonster) {
		int success;
		try {
			success = this.template.update(DDConstants.INSERT_MONSTER_QUERY, newMonster.getCommonName(),
					newMonster.getType(), newMonster.getSubType(), newMonster.getSize(),
					newMonster.getDamageImmunities(), newMonster.getDamageResistances(),
					newMonster.getDamageVulnerabilities(), newMonster.getSenses(), newMonster.getSkills(),
					newMonster.getSavingThrows(), newMonster.getHealth(), newMonster.getArmorClass(),
					newMonster.getChallengeRating(), newMonster.getLanguages(), newMonster.getSpeed(),
					SQLUtils.serializeAbilityScores(newMonster), newMonster.getAbilities(), newMonster.getAttacks(),
					newMonster.getAlignment(), newMonster.getConditionImmunities(), newMonster.getAvgHitDice(),
					newMonster.getExpValue(), newMonster.getLegendaryActions());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "saveMonster: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Helper method to transform the return data from the DB into useable objects
	 * in the Java and Angular code
	 * 
	 * @param rows the object returned by the DB
	 * @return the List of Monsters from the DB as useable objects
	 */
	private List<Monster> transformRawMonsterOutputToList(List<Map<String, Object>> rows) {
		final List<Monster> monsters = new ArrayList<>();
		for (final Map<String, Object> row : rows) {
			final Monster mo = new Monster();
			mo.setCommonName((String) row.get("CommonName"));
			mo.setType((String) row.get("Type"));
			mo.setSubType((String) row.get("SubType"));
			mo.setDamageImmunities((String) row.get("DamageImmunities"));
			mo.setDamageResistances((String) row.get("DamageResistances"));
			mo.setDamageVulnerabilities((String) row.get("DamageVulnerabilities"));
			mo.setSenses((String) row.get("Senses"));
			mo.setSkills((String) row.get("Skills"));
			mo.setSavingThrows((String) row.get("SavingThrows"));
			mo.setHealth((Integer) row.get("Health"));
			mo.setArmorClass((Integer) row.get("AC"));
			mo.setChallengeRating(new BigDecimal(row.get("CR").toString()).doubleValue());
			mo.setLanguages((String) row.get("Languages"));
			mo.setSpeed((String) row.get("Speed"));
			mo.setAbilities((String) row.get("Abilities"));
			mo.setAttacks((String) row.get("Attacks"));
			mo.setSize((String) row.get("Size"));
			mo.setAlignment((String) row.get("Alignment"));
			mo.setConditionImmunities((String) row.get("ConditionImmunities"));
			CreatureUtils.setAbilitiesFromSql(
					SQLUtils.parseAbilities(row.get(DDConstants.ABILITY_SCORES_KEY).toString()), mo);
			mo.setAvgHitDice((String) row.get("AvgHitDice"));
			mo.setExpValue((Integer) row.get("ExpValue"));
			mo.setLegendaryActions((String) row.get("LegendaryActions"));
			monsters.add(mo);
		}
		return monsters;
	}

}
