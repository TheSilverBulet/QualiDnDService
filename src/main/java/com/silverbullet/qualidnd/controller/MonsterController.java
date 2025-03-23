package com.silverbullet.qualidnd.controller;

import java.io.IOException;
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
import com.silverbullet.qualidnd.data.dto.Monster;
import com.silverbullet.qualidnd.response.QDDResponse;

/**
 * Controller class to handle all Monster related functionality for the
 * application
 *
 * @author Batman
 *
 */
@QDDController
public class MonsterController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(MonsterController.class);

	/**
	 * Method to retrieve all monsters
	 *
	 * @return The full list of monsters
	 */
	@GetMapping(value = "/retrieveAllMonsters")
	public ResponseEntity<List<Monster>> retrieveMonsters() {
		final List<Monster> monsters = this.redisService.retrieveMonsterList();
		return CollectionUtils.isEmpty(monsters) ? ResponseEntity.badRequest().body(null)
				: ResponseEntity.ok().body(monsters);
	}

	/**
	 * Method to add a new Monster to the DB
	 * 
	 * @param newMonsterJson the new Monster as a JSON string
	 * @return QDDResponse relating to whether or not the operation was successful
	 */
	@PostMapping("/add/record/monster")
	public QDDResponse addMonsterRecord(@RequestBody String newMonsterJson) {
		JSONObject jObj = null;
		Monster newMonster = null;
		try {
			jObj = new JSONObject(newMonsterJson).getJSONObject("record");
			newMonster = new ObjectMapper().readValue(jObj.toString(), Monster.class);
		} catch (JSONException | IOException e) {
			LOG.error("Error parsing JSON");
			return null;
		}
		if (this.monsterService.saveMonster(newMonster)) {
			this.redisService.refreshMonsterCache();
			return new QDDResponse("Successfully added Monster", true);
		}
		return new QDDResponse("A failure occurred", false);
	}
}
