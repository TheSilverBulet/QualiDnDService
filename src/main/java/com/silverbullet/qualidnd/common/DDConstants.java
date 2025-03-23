package com.silverbullet.qualidnd.common;

public class DDConstants {

	private DDConstants() {
	}

	/** String Constants **/
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String PDF_EXTENSION = ".pdf";
	public static final String REF_FILE_PATH = "static/referenceFiles/";
	public static final String PDF_DATA_TYPE = "application/pdf";
	public static final String STRING_SUB1 = "%s";
	public static final String STRING_SUB2 = "%s%s";
	public static final String STRING_SUB3 = "%s%s%s";
	public static final String FORWARD_SLASH = "/";
	public static final String DBL_COLON = "::";
	public static final String EASY = "Easy";
	public static final String MEDIUM = "Medium";
	public static final String HARD = "Hard";
	public static final String DEADLY = "Deadly";
	public static final String ALL = "All";

	/** Regex constants */
	public static final String REMOVE_DIGITS_REGEX = "\\d";

	/** Custom Annotation Constants **/
	public static final String PASSWORDS_NO_MATCH = "Passwords don't match";
	public static final String INVALID_EMAIL = "Invalid Email";

	/** Config Constants **/
	public static final String LOGIN_KEY = "login";

	/** HTTP Error Constants **/
	public static final String BAD_REQUEST_MSG = "Http Error Code: 400. Bad Request";
	public static final String UNAUTHORIZED_REQUEST_MSG = "Http Error Code: 401. Unauthorized";
	public static final String NOT_FOUND_MSG = "Http Error Code: 404. Resource not found";
	public static final String INTERNAL_ERROR_MSG = "Http Error Code: 500. Internal Server Error";

	/** Template Constants **/
	public static final String ERROR_PAGE = "errorPage";
	public static final String HOME_PAGE = "home";
	public static final String REGISTRATION_PAGE = "registration";
	public static final String REGISTRATION_SUCCESS_PAGE = "registrationSuccess";
	public static final String ROLL_PAGE = "roll";
	public static final String ACCOUNT_PAGE = "myAccount";

	/** Query Constants **/
	/** Initiative Queries */
	public static final String INSERT_INIT_ROLL_QUERY = "INSERT INTO Initiative (username, totalRoll, initialRoll, modifier, currentHealth, current)"
			+ " values (?, ?, ?, ?, ?, ?);";
	public static final String GET_ALL_INIT_ROLL_QUERY = "SELECT * from Initiative;";
	public static final String CLEAR_INIT_TABLE_QUERY = "TRUNCATE TABLE Initiative;";
	public static final String MAX_ROLL_INIT_TABLE_QUERY = "SELECT MAX(roll), username from Initiative GROUP BY username;";
	public static final String GET_CURRENT_HEALTH_QUERY = "SELECT * from Initiative WHERE username=?;";
	public static final String UPDATE_HEALTH_QUERY = "UPDATE Initiative SET currentHealth=? WHERE username=?;";
	public static final String ORDER_INITIATIVE_QUERY = "SELECT * from Initiative ORDER BY totalRoll DESC, initialRoll DESC;";
	public static final String UPDATE_INIT_QUERY = "INSERT INTO Initiative (username, totalRoll, initialRoll, modifier, currentHealth, current) values"
			+ "(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE current=?;";
	public static final String REMOVE_SINGLE_ENTRY_QUERY = "DELETE FROM Initiative WHERE username=?;";

	/** Character Queries */
	public static final String RETRIEVE_CHARACTER_QUERY = "SELECT * FROM Characters WHERE username=? and name=?;";
	public static final String RETRIEVE_ACTIVE_CHARACTER_QUERY = "SELECT * FROM Characters WHERE username=? AND active=1;";
	public static final String INSERT_CHARACTER_QUERY = "INSERT INTO Characters (Username, Name, Race, Level, Classes, AbilityScores, MaxHealth, "
			+ "TemporaryHitPoints, CurrentHealth, Chakra, SpellSlots, Active, DeathSaveSuccess, DeathSaveFailure, ExhaustionPoints, CorruptionPoints, "
			+ "MadnessPoints, MellyMelUses, AddictionPoints, LuckPoints)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	public static final String UPDATE_CHARACTER_QUERY = "INSERT INTO Characters (Username, Name, Race, Classes, Level, MaxHealth, TemporaryHitPoints, "
			+ "CurrentHealth, AbilityScores, Chakra, SpellSlots, Active, DeathSaveSuccess, DeathSaveFailure, ExhaustionPoints, CorruptionPoints, MadnessPoints, MellyMelUses, "
			+ "AddictionPoints, LuckPoints) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Level=?, MaxHealth=?, TemporaryHitPoints=?, "
			+ "CurrentHealth=?, Classes=?, AbilityScores=?, Active=?, SpellSlots=?, Chakra=?;";
	public static final String RETRIEVE_ALL_USER_CHARACTER_QUERY = "SELECT * FROM Characters WHERE username=?;";
	public static final String GET_USER_CURRENT_ACTIVE_CHARACTER_QUERY = "SELECT Name FROM Characters WHERE username=? AND active=1;";
	public static final String MAKE_INACTIVE_QUERY = "UPDATE Characters SET active=0 WHERE username=? AND name=?;";
	public static final String MAKE_CHARACTER_ACTIVE_QUERY = "UPDATE Characters SET active=1 WHERE username=? AND name=?;";
	public static final String DELETE_USER_CHARACTERS_QUERY = "DELETE FROM Characters WHERE username=?;";
	public static final String DELETE_SINGLE_CHARACTER_QUERY = "DELETE FROM Characters WHERE username=? AND name=?;";
	// This query is incomplete it needs to be completed before use in the method
	// that uses it.
	public static final String RETRIEVE_ALL_ACTIVE_CHARS_PARTIAL = "SELECT * FROM Characters WHERE ";
	public static final String RETRIEVE_ALL_CHARACTERS = "SELECT * FROM Characters;";
	public static final String PARTIAL_UPDATE_CHAR_ATTR_QUERY = "UPDATE Characters SET ";
	public static final String UPDATE_CHARACTER_USERNAME_QUERY = "UPDATE Characters SET Username=? WHERE Name=?;";

	/** User Queries */
	public static final String RETRIEVE_USER_QUERY = "SELECT * FROM User WHERE username=?;";
	public static final String INSERT_USER_QUERY = "INSERT INTO User "
			+ "(Id, Username, firstName, lastName, email, password, role) values (?, ?, ?, ?, ?, ?, ?);";
	public static final String UPDATE_USER_PASS_QUERY = "UPDATE User SET password=? WHERE Username=? AND firstName=?;";
	public static final String UPDATE_USER_USERNAME_QUERY = "UPDATE User SET Username=? WHERE Username=? AND firstName=? AND lastName=?;";
	public static final String MAX_ID_USER_TABLE_QUERY = "SELECT MAX(Id) from User;";
	public static final String RETRIEVE_ALL_USERS_QUERY = "SELECT * FROM User;";
	public static final String DELETE_USER_QUERY = "DELETE FROM User Where username=?;";

	/** Ability Queries */
	public static final String RETRIEVE_SPELLS_QUERY = "SELECT * FROM Spells";
	public static final String RETRIEVE_JUTSU_QUERY = "SELECT * FROM Jutsu";
	public static final String INSERT_SPELL_QUERY = "INSERT INTO Spells (SpellName, School, SpellLevel, CastingTime, Components, "
			+ "Duration, `Range`, Description, BardCanCast, BloodHunterCanCast, ClericCanCast, DruidCanCast, PaladinCanCast, RangerCanCast, "
			+ "SorcererCanCast, WarlockCanCast, WizardCanCast, RitualFlag) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	public static final String INSERT_JUTSU_QUERY = "INSERT INTO Jutsu (JutsuName, JutsuRank, `Release`, Duration, Cost, `Range`, Description) VALUES (?, ?, ?, ?, ?, ?, ?);";

	/** File Queries */
	public static final String GET_FILE_QUERY = "SELECT * FROM Files WHERE FileName=?;";
	public static final String UPLOAD_FILE_QUERY = "INSERT INTO Files (FileName, FileData, FileType) VALUES (?, ?, \"Reference\");";

	/** Monster Queries */
	public static final String RETRIEVE_ALL_MONS_QUERY = "SELECT * FROM Monsters;";
	public static final String INSERT_MONSTER_QUERY = "INSERT INTO Monsters (CommonName, Type, SubType, Size, DamageImmunities, DamageResistances, DamageVulnerabilities, "
			+ "Senses, Skills, SavingThrows, Health, AC, CR, Languages, Speed, AbilityScores, Abilities, Attacks, Alignment, ConditionImmunities, AvgHitDice, ExpValue, LegendaryActions) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	/** Query Exception Messages */
	public static final String PROCESS_EXCEPTION = "There was an issue processing the query";

	/** Key Constants **/
	public static final String USERNAME_KEY = "username";
	public static final String ALL_USERS_KEY = "allUsers";
	public static final String NAME_KEY = "name";
	public static final String HEALTH_KEY = "health";
	public static final String TOTAL_ROLL_KEY = "totalRoll";
	public static final String INITIAL_ROLL_KEY = "initialRoll";
	public static final String MODIFIER_KEY = "modifier";
	public static final String MAX_HEALTH_KEY = "maxHealth";
	public static final String TEMPORARY_HIT_POINTS_KEY = "temporaryHitPoints";
	public static final String CURRENT_HEALTH_KEY = "currentHealth";
	public static final String CURRENT_KEY = "current";
	public static final String ACTIVE_KEY = "active";
	public static final String RACE_KEY = "race";
	public static final String LEVEL_KEY = "level";
	public static final String STRENGTH_KEY = "strength";
	public static final String DEXTERITY_KEY = "dexterity";
	public static final String CONSTITUTION_KEY = "constitution";
	public static final String INTELLIGENCE_KEY = "intelligence";
	public static final String WISDOM_KEY = "wisdom";
	public static final String CHARISMA_KEY = "charisma";
	public static final String FIRST_NAME_KEY = "firstName";
	public static final String LAST_NAME_KEY = "lastName";
	public static final String EMAIL_KEY = "email";
	public static final String PASSWORD_KEY = "password";
	public static final String USER_KEY = "user";
	public static final String USER_ROLE_KEY = "USER";
	public static final String ADMIN_ROLE_KEY = "ADMIN";
	public static final String INIT_LIST_KEY = "initList";
	public static final String VIEW_KEY = "view";
	public static final String INITIATIVE_KEY = "initiative";
	public static final String INITIATIVE_TABLE_KEY = "initiativeTable";
	public static final String CHARACTER_KEY = "character";
	public static final String CHARACTERS_KEY = "characters";
	public static final String ROLE_KEY = "role";
	public static final String ID_KEY = "Id";
	public static final String BARBARIAN_KEY = "barbarian";
	public static final String BARD_KEY = "bard";
	public static final String BLOODHUNTER_KEY = "bloodHunter";
	public static final String CLERIC_KEY = "cleric";
	public static final String DRUID_KEY = "druid";
	public static final String FIGHTER_KEY = "fighter";
	public static final String GUNSLINGER_KEY = "gunslinger";
	public static final String MONK_KEY = "monk";
	public static final String PALADIN_KEY = "paladin";
	public static final String RANGER_KEY = "ranger";
	public static final String REVISED_RANGER_KEY = "revisedRanger";
	public static final String ROGUE_KEY = "rogue";
	public static final String SORCERER_KEY = "sorcerer";
	public static final String WARLOCK_KEY = "warlock";
	public static final String WIZARD_KEY = "wizard";
	public static final String DEATH_SAVE_SUCCESS_KEY = "deathSaveSuccess";
	public static final String DEATH_SAVE_FAILURE_KEY = "deathSaveFailure";
	public static final String EXHAUSTION_POINTS_KEY = "exhaustionPoints";
	public static final String CORRUPTION_POINTS_KEY = "corruptionPoints";
	public static final String MADNESS_POINTS_KEY = "madnessPoints";
	public static final String MELLY_MEL_USES_KEY = "mellyMelUses";
	public static final String ADDICTION_POINTS_KEY = "addictionPoints";
	public static final String LUCK_POINTS_KEY = "luckPoints";
	public static final String CHAKRA_KEY = "Chakra";
	public static final String CLASSES_KEY = "Classes";
	public static final String ABILITY_SCORES_KEY = "AbilityScores";
}
