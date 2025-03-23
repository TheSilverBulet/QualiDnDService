package com.silverbullet.qualidnd.util;

import java.util.HashMap;
import java.util.Map;

import com.silverbullet.qualidnd.data.dto.Ability;
import com.silverbullet.qualidnd.data.dto.ICreature;

/**
 * Utility class that has many creature related functions
 *
 * @author Batman
 *
 */
public class CreatureUtils {

	private CreatureUtils() {

	}

	/**
	 * static map that holds the correct modifier keyed by ability score
	 */
	private static Map<Integer, Integer> modifierMap;

	/**
	 * Static instantiation of the modifier map
	 */
	static {
		modifierMap = new HashMap<>();
		modifierMap.put(0, -5);
		modifierMap.put(2, -4);
		modifierMap.put(4, -3);
		modifierMap.put(6, -2);
		modifierMap.put(8, -1);
		modifierMap.put(10, 0);
		modifierMap.put(12, 1);
		modifierMap.put(14, 2);
		modifierMap.put(16, 3);
		modifierMap.put(18, 4);
		modifierMap.put(20, 5);
		modifierMap.put(22, 6);
		modifierMap.put(24, 7);
		modifierMap.put(26, 8);
		modifierMap.put(28, 9);
		modifierMap.put(30, 10);
	}

	/**
	 * Method to get the correct modifier based on the provided score
	 *
	 * @param rawValue the ability score
	 * @return the corresponding modifier
	 */
	public static int getModifier(int rawValue) {
		if (rawValue % 2 == 1) {
			rawValue = rawValue - 1;
		}
		return modifierMap.get(rawValue);
	}

	/**
	 * Helper method to retrieve Ability Scores from a map and insert them into the
	 * creature object
	 *
	 * @param abilityMap the map to retrieve values from
	 * @param creature   the creature to apply values to
	 */
	public static void setAbilitiesFromSql(Map<String, Ability> abilityMap, ICreature creature) {
		creature.setStrength(abilityMap.get("strength"));
		creature.setDexterity(abilityMap.get("dexterity"));
		creature.setConstitution(abilityMap.get("constitution"));
		creature.setIntelligence(abilityMap.get("intelligence"));
		creature.setWisdom(abilityMap.get("wisdom"));
		creature.setCharisma(abilityMap.get("charisma"));
	}

	/**
	 * Method to validate that the correct ability score and modifier pair are set
	 * for each ability for a given creature
	 *
	 * @param creature the creature to ensure integrity for
	 */
	public static void ensureModIntegrity(ICreature creature) {
		final Ability str = new Ability();
		str.setAbilityScore(creature.getStrength().getAbilityScore());
		str.setAbilityModifier();
		creature.setStrength(str);
		final Ability dex = new Ability();
		dex.setAbilityScore(creature.getDexterity().getAbilityScore());
		dex.setAbilityModifier();
		creature.setDexterity(dex);
		final Ability con = new Ability();
		con.setAbilityScore(creature.getConstitution().getAbilityScore());
		con.setAbilityModifier();
		creature.setConstitution(con);
		final Ability intel = new Ability();
		intel.setAbilityScore(creature.getIntelligence().getAbilityScore());
		intel.setAbilityModifier();
		creature.setIntelligence(intel);
		final Ability wis = new Ability();
		wis.setAbilityScore(creature.getWisdom().getAbilityScore());
		wis.setAbilityModifier();
		creature.setWisdom(wis);
		final Ability cha = new Ability();
		cha.setAbilityScore(creature.getCharisma().getAbilityScore());
		cha.setAbilityModifier();
		creature.setCharisma(cha);
	}
}
