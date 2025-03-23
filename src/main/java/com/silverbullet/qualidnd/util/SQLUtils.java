package com.silverbullet.qualidnd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.silverbullet.qualidnd.data.dto.Ability;
import com.silverbullet.qualidnd.data.dto.CharacterClass;
import com.silverbullet.qualidnd.data.dto.ICreature;
import com.silverbullet.qualidnd.data.dto.SpellSlots;

/**
 * Utility class to help ensure things are always stored and retrieved uniformly
 * from the DB
 *
 * @author Batman
 *
 */
public class SQLUtils {

	private static final Logger LOG = LogManager.getLogger(SQLUtils.class);

	private static final String[] abilityList = { "strength", "dexterity", "constitution", "intelligence", "wisdom",
			"charisma" };

	private SQLUtils() {
	}

	public static final String[] classList = new String[] { "Barbarian", "Bard", "Blood Hunter", "Cleric", "Druid",
			"Fighter", "Gunslinger", "Monk", "Paladin", "Ranger", "Rogue", "Shinobi", "Sorcerer", "Warlock", "Witcher",
			"Wizard" };

	/**
	 * End result should produce a proper list of CharacterClass elements from a
	 * JSON String
	 *
	 * @param classString
	 * @return The list of classes associated with a single character
	 */
	public static final List<CharacterClass> parseClassString(String classString) {
		LOG.info("Parsing class string");
		final JsonObject jsonObject = JsonParser.parseString(classString).getAsJsonObject();
		final JsonArray jEle = jsonObject.getAsJsonArray("classes");
		final Iterator<JsonElement> iterator = jEle.iterator();
		final List<CharacterClass> charClasses = new ArrayList<>();
		while (iterator.hasNext()) {
			final JsonElement ele = iterator.next();
			final CharacterClass cl = new CharacterClass();
			cl.setClassName(ele.getAsJsonObject().get("className").getAsString());
			cl.setClassLevel(ele.getAsJsonObject().get("classLevel").getAsInt());
			charClasses.add(cl);
		}
		return charClasses;
	}

	/**
	 * End result should be an object in the form
	 *
	 * <pre>
	 * {
	 * 	"classes": [
	 * 		{
	 * 			"className": "Barbarian",
	 * 			"classLevel": 5
	 * 		}
	 * 		.
	 * 		.
	 * 		.
	 * 	]
	 * }
	 * </pre>
	 *
	 * @param classes
	 * @return
	 */
	public static final String serializeClassString(List<CharacterClass> classes) {
		LOG.info("Serializing classes");
		final JsonObject j = new JsonObject();
		j.add("classes", new Gson().toJsonTree(classes));
		return j.toString();
	}

	/**
	 * End result should produce a proper list of SpellSlots elements from a JSON
	 * String
	 *
	 * @param classString
	 * @return list of spell slots associated with a specific character
	 */
	public static final List<SpellSlots> parseSpellSlots(String spellSlotsString) {
		LOG.info("Parsing spell slots");
		final JsonObject jsonObject = JsonParser.parseString(spellSlotsString).getAsJsonObject();
		final JsonArray jEle = jsonObject.getAsJsonArray("spellSlots");
		final Iterator<JsonElement> iterator = jEle.iterator();
		final List<SpellSlots> slots = new ArrayList<>();
		while (iterator.hasNext()) {
			final JsonElement ele = iterator.next();
			final SpellSlots sp = new SpellSlots();
			sp.setSlotLevel(ele.getAsJsonObject().get("slotLevel").getAsInt());
			sp.setNumberOfSlots(ele.getAsJsonObject().get("NumberofSlots").getAsInt());
			slots.add(sp);
		}
		return slots;
	}

	/**
	 * End result should be an object in the form
	 *
	 * <pre>
	 * {
	 * 	"spellSlots": [
	 * 		{
	 * 			"slotLevel": "1",
	 * 			"NumberofSlots": 2
	 * 		}
	 * 		.
	 * 		.
	 * 		.
	 * 	]
	 * }
	 * </pre>
	 *
	 * @param classes
	 * @return
	 */
	public static final String serializeSpellSlotString(List<SpellSlots> spellSlots) {
		LOG.info("Serializing Spells");
		final JsonObject j = new JsonObject();
		j.add("spellSlots", new Gson().toJsonTree(spellSlots));
		return j.toString();
	}

	/**
	 * End result should produce a proper list of SpellSlots elements from a JSON
	 * String
	 *
	 * @param classString
	 * @return
	 */
	public static final Map<String, Ability> parseAbilities(String abilityString) {
		LOG.info("Parsing abilities");
		final Map<String, Ability> abilityMap = new HashMap<>();
		final JsonObject jsonObject = JsonParser.parseString(abilityString).getAsJsonObject();
		for (final String ability : abilityList) {
			final Ability ab = new Ability();
			ab.setAbilityScore(jsonObject.get(ability).getAsJsonObject().get("abilityScore").getAsInt());
			ab.setAbilityModifier(jsonObject.get(ability).getAsJsonObject().get("abilityModifier").getAsInt());
			abilityMap.put(ability, ab);
		}
		return abilityMap;
	}

	/**
	 * End result should be an object in the form
	 *
	 * <pre>
	 * {
	 * 	"strength": {
	 * 		"abilityScore": 16,
	 * 		"abilityModifier": 3
	 *	}
	 * 		.
	 * 		.
	 * 		.
	 * }
	 * </pre>
	 *
	 * @param classes
	 * @return
	 */
	public static final String serializeAbilityScores(ICreature creature) {
		LOG.info("Serializing ability scores");
		final JsonObject j = new JsonObject();
		j.add("strength", new Gson().toJsonTree(creature.getStrength()));
		j.add("dexterity", new Gson().toJsonTree(creature.getDexterity()));
		j.add("constitution", new Gson().toJsonTree(creature.getConstitution()));
		j.add("intelligence", new Gson().toJsonTree(creature.getIntelligence()));
		j.add("wisdom", new Gson().toJsonTree(creature.getWisdom()));
		j.add("charisma", new Gson().toJsonTree(creature.getCharisma()));
		return j.toString();
	}

}
