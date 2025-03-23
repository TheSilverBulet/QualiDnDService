package com.silverbullet.qualidnd.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.data.dto.Jutsu;
import com.silverbullet.qualidnd.data.dto.Spell;
import com.silverbullet.qualidnd.response.QDDResponse;

/**
 * Controller to handle all Ability (Spells, Jutsu) functionality
 *
 * @author Batman
 *
 */
@QDDController
public class AbilityController extends BaseController implements Serializable {

	private static final long serialVersionUID = -1902429112723101910L;
	private static final Logger LOG = LogManager.getLogger(AbilityController.class);

	/**
	 * Method to retrieve all spells
	 *
	 * @return The full list of spells
	 */
	@GetMapping(value = "/retrieveAllSpells")
	public List<Spell> retrieveSpells() {
		final List<Spell> spells = (List<Spell>) this.redisService.retrieveAbilityList(true);
		return CollectionUtils.isEmpty(spells) ? new ArrayList<>() : spells;
	}

	/**
	 * Method to retrieve all jutsu
	 *
	 * @return The full list of jutsu
	 */
	@GetMapping(value = "/retrieveAllJutsu")
	public ResponseEntity<List<Jutsu>> retrieveJutsu() {
		final List<Jutsu> jutsu = (List<Jutsu>) this.redisService.retrieveAbilityList(false);
		return CollectionUtils.isEmpty(jutsu) ? ResponseEntity.badRequest().body(null)
				: ResponseEntity.ok().body(jutsu);
	}

	/**
	 * Method to add a jutsu to the database
	 *
	 * @return Successful or unsuccessful
	 */
	@PostMapping(value = "/add/record/jutsu")
	public QDDResponse addJutsu(@RequestBody String newJutsuJson) {
		JSONObject jObj = null;
		Jutsu newJutsu = null;
		try {
			jObj = new JSONObject(newJutsuJson).getJSONObject("record");
			newJutsu = new ObjectMapper().readValue(jObj.toString(), Jutsu.class);
		} catch (JSONException | IOException e) {
			LOG.error("Error parsing JSON");
			return null;
		}
		if (this.abilityService.saveAbility(newJutsu, false)) {
			this.redisService.refreshAbilityCache();
			return new QDDResponse("Successfully added Spell", true);
		}
		return new QDDResponse("A failure occurred", false);
	}

	/**
	 * Method to add a spell to the database
	 *
	 * @return Successful or unsuccessful
	 */
	@PostMapping(value = "/add/record/spell")
	public QDDResponse addSpell(@RequestBody String newSpellJson) {
		JSONObject jObj = null;
		Spell newSpell = null;
		try {
			jObj = new JSONObject(newSpellJson).getJSONObject("record");
			newSpell = new ObjectMapper().readValue(jObj.toString(), Spell.class);
		} catch (JSONException | IOException e) {
			LOG.error("Error parsing JSON");
			return null;
		}
		if (this.abilityService.saveAbility(newSpell, true)) {
			this.redisService.refreshAbilityCache();
			return new QDDResponse("Successfully added Spell", true);
		}
		return new QDDResponse("A failure occurred", false);
	}
}
