package com.silverbullet.qualidnd.controller;

import static com.silverbullet.qualidnd.common.DDConstants.ALL;
import static com.silverbullet.qualidnd.common.DDConstants.DEADLY;
import static com.silverbullet.qualidnd.common.DDConstants.EASY;
import static com.silverbullet.qualidnd.common.DDConstants.HARD;
import static com.silverbullet.qualidnd.common.DDConstants.MEDIUM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.silverbullet.qualidnd.annotations.QDDController;
import com.silverbullet.qualidnd.common.DEC;

/**
 * Controller to handle generic api calls for general info
 *
 * @author Batman
 *
 */
@QDDController
public class ApplicationRestController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(ApplicationRestController.class);

	@Value("${spring.datasource.username}")
	public String datasourceUsername;

	@Value("${azure.keyvault.uri}")
	public String azureKeyvaultUri;

	/**
	 * Mapping to build the Difficulty Encounter Calculator ratings for the party
	 *
	 * @param formMap the map relative to the form the admin filled-in to make this
	 *                request
	 * @return List of Encounter budgets
	 */
	@PostMapping(value = "/dec/budget")
	public List<Integer> getThresholds(@RequestBody Map<String, Object> formMap) {
		final String threshold = formMap.get("threshold").toString();
		final List<String> selectedCharacters = (List<String>) formMap.get("selectedNames");
		List<Integer> thresholds = new ArrayList<>();
		if (StringUtils.contains(threshold, EASY)) {
			thresholds = DEC.buildXpBudgetForSingleDifficulty(
					this.characterService.retrieveActiveCharacterLevels(selectedCharacters), EASY);
		} else if (StringUtils.contains(threshold, MEDIUM)) {
			thresholds = DEC.buildXpBudgetForSingleDifficulty(
					this.characterService.retrieveActiveCharacterLevels(selectedCharacters), MEDIUM);
		} else if (StringUtils.contains(threshold, HARD)) {
			thresholds = DEC.buildXpBudgetForSingleDifficulty(
					this.characterService.retrieveActiveCharacterLevels(selectedCharacters), HARD);
		} else if (StringUtils.contains(threshold, DEADLY)) {
			thresholds = DEC.buildXpBudgetForSingleDifficulty(
					this.characterService.retrieveActiveCharacterLevels(selectedCharacters), DEADLY);
		} else if (StringUtils.contains(threshold, ALL)) {
			thresholds = DEC.buildXpBudgetForAllDifficulties(
					this.characterService.retrieveActiveCharacterLevels(selectedCharacters));
		}
		return thresholds;
	}

	/**
	 * Mapping to check the app is connected and running on the server. If the app
	 * is running correctly the Datasource username, and keyvault URI will be
	 * displayed. If it's displayed the app is running and able to connect to the
	 * keyvault and DB.
	 *
	 * @return String representation of the above information
	 */
	@GetMapping("/health")
	public String healthCheck() {
		return "Datasource Username: " + this.datasourceUsername + "\n" + "Keyvault URI: " + this.azureKeyvaultUri;
	}

	/**
	 * Mapping to refresh the cache of the given type, or just refresh all caches.
	 *
	 * @param type the cache type to refresh
	 */
	@GetMapping("/refreshCache/{type}")
	public void refreshRedisCache(@PathVariable(value = "type") final String type) {
		if (StringUtils.equalsIgnoreCase(type, "all")) {
			this.redisService.refreshCache();
		} else if (StringUtils.equalsIgnoreCase(type, "monster")) {
			this.redisService.refreshMonsterCache();
		} else if (StringUtils.equalsIgnoreCase(type, "init")) {
			this.redisService.refreshInitCache();
		} else if (StringUtils.equalsIgnoreCase(type, "ability")) {
			this.redisService.refreshAbilityCache();
		}
	}

}
