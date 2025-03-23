package com.silverbullet.qualidnd.data.dto;

/**
 * Interface to represent creatures can be characters or Monsters
 * 
 * @author Batman
 *
 */
public interface ICreature {

	Ability getStrength();

	Ability getDexterity();

	Ability getConstitution();

	Ability getIntelligence();

	Ability getWisdom();

	Ability getCharisma();

	void setStrength(Ability strength);

	void setDexterity(Ability dexterity);

	void setConstitution(Ability constitution);

	void setIntelligence(Ability intelligence);

	void setWisdom(Ability wisdom);

	void setCharisma(Ability charisma);
}
