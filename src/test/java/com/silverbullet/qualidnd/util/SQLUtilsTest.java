package com.silverbullet.qualidnd.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.silverbullet.qualidnd.data.dto.Ability;
import com.silverbullet.qualidnd.data.dto.CharacterClass;
import com.silverbullet.qualidnd.data.dto.CharacterDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SQLUtilsTest {

	private static final Logger LOG = LogManager.getLogger(SQLUtilsTest.class);

//	private static final String[] abilityList = { "strength", "dexterity", "constitution", "intelligence", "wisdom",
//			"charisma" };

	private static final String EXPECTED_CLASSLIST_JSON = "{\"classes\":[{\"className\":\"Barbarian\",\"classLevel\":5},{\"className\":\"Fighter\",\"classLevel\":5}]}";
	private static final String EXPECTED_ABILITY_JSON = "{\"strength\":{\"abilityScore\":16,\"abilityModifier\":3},\"dexterity\":{\"abilityScore\":16,\"abilityModifier\":3},\"constitution\":{\"abilityScore\":16,\"abilityModifier\":3},\"intelligence\":{\"abilityScore\":16,\"abilityModifier\":3},\"wisdom\":{\"abilityScore\":16,\"abilityModifier\":3},\"charisma\":{\"abilityScore\":16,\"abilityModifier\":3}}";

	@Test
	public void testSerializeClassList() {
		CharacterClass barbarian = new CharacterClass();
		barbarian.setClassName("Barbarian");
		barbarian.setClassLevel(5);
		CharacterClass fighter = new CharacterClass();
		fighter.setClassName("Fighter");
		fighter.setClassLevel(5);
		List<CharacterClass> classList = new ArrayList<>();
		classList.add(barbarian);
		classList.add(fighter);
		String classListJson = SQLUtils.serializeClassString(classList);
		assertTrue(StringUtils.equalsIgnoreCase(classListJson, EXPECTED_CLASSLIST_JSON));
	}

	@Test
	public void testParseClasses() {
		CharacterClass barbarian = new CharacterClass();
		barbarian.setClassName("Barbarian");
		barbarian.setClassLevel(5);
		CharacterClass fighter = new CharacterClass();
		fighter.setClassName("Fighter");
		fighter.setClassLevel(5);
		List<CharacterClass> classList = new ArrayList<>();
		classList.add(barbarian);
		classList.add(fighter);
		List<CharacterClass> actualList = SQLUtils.parseClassString(EXPECTED_CLASSLIST_JSON);
		LOG.error(actualList);
	}

	@Test
	public void testSerializeAbilities() {
		CharacterDto chara = new CharacterDto();
		Ability ab = new Ability();
		ab.setAbilityScore(16);
		ab.setAbilityModifier(3);
		chara.setStrength(ab);
		chara.setDexterity(ab);
		chara.setConstitution(ab);
		chara.setIntelligence(ab);
		chara.setWisdom(ab);
		chara.setCharisma(ab);
		String abilityJson = SQLUtils.serializeAbilityScores(chara);
		LOG.error(abilityJson);
		// assertTrue(StringUtils.equalsIgnoreCase(classListJson, EXPECTED_JSON));
	}

	@Test
	public void testParseAbilites() {
		Map<String, Ability> abilityMap = SQLUtils.parseAbilities(EXPECTED_ABILITY_JSON);
		LOG.error(abilityMap);
	}

}
