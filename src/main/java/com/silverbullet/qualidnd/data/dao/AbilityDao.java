package com.silverbullet.qualidnd.data.dao;

import static com.silverbullet.qualidnd.common.DDConstants.PROCESS_EXCEPTION;
import static com.silverbullet.qualidnd.common.DDConstants.RETRIEVE_JUTSU_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.RETRIEVE_SPELLS_QUERY;
import static com.silverbullet.qualidnd.common.DDConstants.STRING_SUB3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.silverbullet.qualidnd.common.DDConstants;
import com.silverbullet.qualidnd.data.dto.Jutsu;
import com.silverbullet.qualidnd.data.dto.Spell;

/**
 * DAO class that handles functionality relating to Abilities (Jutsu, Spells)
 *
 * @author Batman
 *
 */
@Repository
public class AbilityDao extends BaseDao {

	private static final Logger LOG = LogManager.getLogger(AbilityDao.class);

	/**
	 * Method to retrieve the whole spell list
	 *
	 * @return the full spell list if successful, else empty list
	 */
	public List<Spell> getAllSpells() {
		List<Spell> spells = new ArrayList<>();

		try {
			final List<Map<String, Object>> rows = this.template.queryForList(RETRIEVE_SPELLS_QUERY);

			spells = this.transformRawSpellOutputToList(rows);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "getAllSpells: ", PROCESS_EXCEPTION, dae.getMessage()));
		}

		return spells;
	}

	/**
	 * Method to retrieve the whole Jutsu list
	 *
	 * @return the full Jutsu list if successful, else empty list
	 */
	public List<Jutsu> getAllJutsu() {
		List<Jutsu> jutsu = new ArrayList<>();

		try {
			final List<Map<String, Object>> rows = this.template.queryForList(RETRIEVE_JUTSU_QUERY);

			jutsu = this.transformRawJutsuOutputToList(rows);
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "getAllSpells: ", PROCESS_EXCEPTION, dae.getMessage()));
		}

		return jutsu;
	}

	/**
	 * Method to save a new Jutsu
	 *
	 * @param jutsu the Jutsu to insert
	 * @return true if successful, else false
	 */
	public boolean saveJutsu(Jutsu jutsu) {
		int success;
		try {
			success = this.template.update(DDConstants.INSERT_JUTSU_QUERY, jutsu.getJutsuName(), jutsu.getRank(),
					jutsu.getRelease(), jutsu.getDuration(), jutsu.getCost(), jutsu.getRange(), jutsu.getDescription());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "saveJutsu: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Method to save a new Spell
	 *
	 * @param spell the spell to save
	 * @return true if successful, else false
	 */
	public boolean saveSpell(Spell spell) {
		int success;
		try {
			success = this.template.update(DDConstants.INSERT_SPELL_QUERY, spell.getSpellName(), spell.getSpellSchool(),
					spell.getSpellLevel(), spell.getCastingTime(), spell.getComponents(), spell.getDuration(),
					spell.getRange(), spell.getDescription(), spell.getBardCanCast(), spell.getBloodHunterCanCast(),
					spell.getClericCanCast(), spell.getDruidCanCast(), spell.getPaladinCanCast(),
					spell.getRangerCanCast(), spell.getSorcererCanCast(), spell.getWarlockCanCast(),
					spell.getWizardCanCast(), spell.getIsRitual());
		} catch (final DataAccessException dae) {
			LOG.error(String.format(STRING_SUB3, "saveSpell: ", PROCESS_EXCEPTION, dae.getMessage()));
			success = -999;
		}
		return success >= 0;
	}

	/**
	 * Mapper function to map the retrieved list from the DB to a List of Spells
	 *
	 * @param rows the list map retrieved from the DB
	 * @return the full list of Spells
	 */
	private List<Spell> transformRawSpellOutputToList(List<Map<String, Object>> rows) {
		final List<Spell> spells = new ArrayList<>();
		for (final Map<String, Object> row : rows) {
			final Spell spell = new Spell();
			spell.setSpellName(row.get("SpellName").toString());
			spell.setSpellSchool(row.get("School").toString());
			spell.setSpellLevel(Integer.parseInt(row.get("SpellLevel").toString()));
			spell.setCastingTime(row.get("CastingTime").toString());
			spell.setRange(row.get("Range").toString());
			spell.setComponents(row.get("Components").toString());
			spell.setDuration(row.get("Duration").toString());
			spell.setDescription(row.get("Description").toString());
			spell.setIsRitual((int) row.get("RitualFlag") == 1 ? true : false);
			spell.setBardCanCast((int) row.get("BardCanCast") == 1 ? true : false);
			spell.setBloodHunterCanCast((int) row.get("BloodHunterCanCast") == 1 ? true : false);
			spell.setClericCanCast((int) row.get("ClericCanCast") == 1 ? true : false);
			spell.setDruidCanCast((int) row.get("DruidCanCast") == 1 ? true : false);
			spell.setPaladinCanCast((int) row.get("PaladinCanCast") == 1 ? true : false);
			spell.setRangerCanCast((int) row.get("RangerCanCast") == 1 ? true : false);
			spell.setSorcererCanCast((int) row.get("SorcererCanCast") == 1 ? true : false);
			spell.setWarlockCanCast((int) row.get("WarlockCanCast") == 1 ? true : false);
			spell.setWizardCanCast((int) row.get("WizardCanCast") == 1 ? true : false);
			spells.add(spell);
		}
		return spells;
	}

	/**
	 * Mapper function to map the retrieved list from the DB to a List of Jutsu
	 *
	 * @param rows the list map retrieved from the DB
	 * @return the full list of Jutsu
	 */
	private List<Jutsu> transformRawJutsuOutputToList(List<Map<String, Object>> rows) {
		final List<Jutsu> jutsu = new ArrayList<>();
		for (final Map<String, Object> row : rows) {
			final Jutsu ju = new Jutsu();
			ju.setJutsuName(row.get("JutsuName").toString());
			ju.setRelease(row.get("Release").toString());
			ju.setRank(row.get("JutsuRank").toString());
			ju.setCost(Integer.parseInt(row.get("Cost").toString()));
			ju.setRange(row.get("Range").toString());
			ju.setDuration(row.get("Duration").toString());
			ju.setDescription(row.get("Description").toString());
			jutsu.add(ju);
		}
		return jutsu;
	}

}
