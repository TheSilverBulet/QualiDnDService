package com.silverbullet.qualidnd.common;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DEC {

    private static final String EASY = "Easy";
    private static final String MEDIUM = "Medium";
    private static final String HARD = "Hard";
    private static final String DEADLY = "Deadly";

    /**
     * Purely for assessing difficulty, doesn't change EXP yield. Monsters that are significantly weaker
     * than average should not be included. These are the multipliers based on a 3-5 adventurer party.
     */
    private static Map<Integer, Double> createEncounterMultiplierMap(){
        Map<Integer, Double> tmpMap = new HashMap<>();
        tmpMap.put(1, 1.0);
        tmpMap.put(2, 1.5);
        tmpMap.put(3, 2.0);
        tmpMap.put(4, 2.0);
        tmpMap.put(5, 2.0);
        tmpMap.put(6, 2.0);
        tmpMap.put(7, 2.5);
        tmpMap.put(8, 2.5);
        tmpMap.put(9, 2.5);
        tmpMap.put(10, 2.5);
        tmpMap.put(11, 3.0);
        tmpMap.put(12, 3.0);
        tmpMap.put(13, 3.0);
        tmpMap.put(14, 3.0);
        tmpMap.put(15, 4.0);
        return tmpMap;
    }

    /**
     * Builder function to create the xp threshold for an easy encounter
     * @return
     *      The list of xp thresholds for an easy encounter where the index of the threshold
     *      + 1 (index + 1) is the level of the party
     */
    private static List<Integer> createEasyThreshold(){
        List<Integer> tmpList = new ArrayList<>();
        tmpList.add(25);
        tmpList.add(50);
        tmpList.add(75);
        tmpList.add(125);
        tmpList.add(250);
        tmpList.add(300);
        tmpList.add(350);
        tmpList.add(450);
        tmpList.add(550);
        tmpList.add(600);
        tmpList.add(800);
        tmpList.add(1000);
        tmpList.add(1100);
        tmpList.add(1250);
        tmpList.add(1400);
        tmpList.add(1600);
        tmpList.add(2000);
        tmpList.add(2100);
        tmpList.add(2400);
        tmpList.add(2800);
        return tmpList;
    }

    /**
     * Builder function to create the xp threshold for an medium encounter
     * @return
     *      The list of xp thresholds for an easy encounter where the index of the threshold
     *      + 1 (index + 1) is the level of the party
     */
    private static List<Integer> createMediumThreshold(){
        List<Integer> tmpList = new ArrayList<>();
        tmpList.add(50);
        tmpList.add(100);
        tmpList.add(150);
        tmpList.add(250);
        tmpList.add(500);
        tmpList.add(600);
        tmpList.add(750);
        tmpList.add(900);
        tmpList.add(1100);
        tmpList.add(1200);
        tmpList.add(1600);
        tmpList.add(2000);
        tmpList.add(2200);
        tmpList.add(2500);
        tmpList.add(2800);
        tmpList.add(3200);
        tmpList.add(3900);
        tmpList.add(4200);
        tmpList.add(4900);
        tmpList.add(5700);
        return tmpList;
    }

    /**
     * Builder function to create the xp threshold for an hard encounter
     * @return
     *      The list of xp thresholds for an easy encounter where the index of the threshold
     *      + 1 (index + 1) is the level of the party
     */
    private static List<Integer> createHardThreshold(){
        List<Integer> tmpList = new ArrayList<>();
        tmpList.add(75);
        tmpList.add(150);
        tmpList.add(225);
        tmpList.add(375);
        tmpList.add(750);
        tmpList.add(900);
        tmpList.add(1100);
        tmpList.add(1400);
        tmpList.add(1600);
        tmpList.add(1900);
        tmpList.add(2400);
        tmpList.add(3000);
        tmpList.add(3400);
        tmpList.add(3800);
        tmpList.add(4300);
        tmpList.add(4800);
        tmpList.add(5900);
        tmpList.add(6300);
        tmpList.add(7300);
        tmpList.add(8500);
        return tmpList;
    }

    /**
     * Builder function to create the xp threshold for an deadly encounter
     * @return
     *      The list of xp thresholds for an easy encounter where the index of the threshold
     *      + 1 (index + 1) is the level of the party
     */
    private static List<Integer> createDeadlyThreshold(){
        List<Integer> tmpList = new ArrayList<>();
        tmpList.add(100);
        tmpList.add(200);
        tmpList.add(400);
        tmpList.add(500);
        tmpList.add(1100);
        tmpList.add(1400);
        tmpList.add(1700);
        tmpList.add(2100);
        tmpList.add(2400);
        tmpList.add(2800);
        tmpList.add(3600);
        tmpList.add(4500);
        tmpList.add(5100);
        tmpList.add(5700);
        tmpList.add(6400);
        tmpList.add(7200);
        tmpList.add(8800);
        tmpList.add(9500);
        tmpList.add(10900);
        tmpList.add(12700);
        return tmpList;
    }

    /**
     * Builder function to create the xp threshold map for all encounters where the key is difficulty level
     * Easy, Medium, Hard, or Deadly, and the value is a list of xp thresholds for the level of the character, where the
     * index + 1 is the level of the character
     * @return
     *      The map of encounter difficulty thresholds
     */
    private static Map<String, List<Integer>> buildXpThresholdMap(){
        Map<String, List<Integer>> tmpMap = new HashMap<>();
        tmpMap.put(EASY, DEC.createEasyThreshold());
        tmpMap.put(MEDIUM, DEC.createMediumThreshold());
        tmpMap.put(HARD, DEC.createHardThreshold());
        tmpMap.put(DEADLY, DEC.createDeadlyThreshold());
        return tmpMap;
    }

    /**
     * Builder to create the conversion map that converts the monster's CR to an XP value
     * @return
     *      The instantiated Map
     */
    private static Map<Double, Integer> buildCRConversionMap(){
        Map<Double, Integer> tmpMap = new HashMap<>();
        tmpMap.put(0.0, 10);
        tmpMap.put(.125, 25);
        tmpMap.put(.25, 50);
        tmpMap.put(.5, 100);
        tmpMap.put(1.0, 200);
        tmpMap.put(2.0, 450);
        tmpMap.put(3.0, 700);
        tmpMap.put(4.0, 1100);
        tmpMap.put(5.0, 1800);
        tmpMap.put(6.0, 2300);
        tmpMap.put(7.0, 2900);
        tmpMap.put(8.0, 3900);
        tmpMap.put(9.0, 5000);
        tmpMap.put(10.0, 5900);
        tmpMap.put(11.0, 7200);
        tmpMap.put(12.0, 8400);
        tmpMap.put(13.0, 10000);
        tmpMap.put(14.0, 11500);
        tmpMap.put(15.0, 13000);
        tmpMap.put(16.0, 15000);
        tmpMap.put(17.0, 18000);
        tmpMap.put(18.0, 20000);
        tmpMap.put(19.0, 22000);
        tmpMap.put(20.0, 25000);
        tmpMap.put(21.0, 33000);
        tmpMap.put(22.0, 41000);
        tmpMap.put(23.0, 50000);
        tmpMap.put(24.0, 62000);
        tmpMap.put(25.0, 75000);
        tmpMap.put(26.0, 90000);
        tmpMap.put(27.0, 105000);
        tmpMap.put(28.0, 120000);
        tmpMap.put(29.0, 135000);
        tmpMap.put(30.0, 155000);
        return tmpMap;
    }

    /**
     * Builder function to create an XP Budget based on a list of party levels (where the size of the list is the number
     * of party members) and each index holds that character's level.
     * @param partyLevels
     *      List of party members' levels
     * @param difficulty
     *      The difficulty of the encounter
     * @return
     *      The total XP budget
     */
    public static List<Integer> buildXpBudgetForSingleDifficulty(final List<Integer> partyLevels, final String difficulty){
        Map<String, List<Integer>> thresholdMap = buildXpThresholdMap();
        Integer totalBudget = 0;
        List<Integer> budget = new ArrayList<>();
        for (Integer i : partyLevels){
            totalBudget+= thresholdMap.get(difficulty).get(i-1);
        }
        budget.add(totalBudget);
        return budget;
    }

    /**
     * Builder function to create XP Budgets for all difficulties based on the party levels given.
     * @param partyLevels
     *      The list of the party's members
     * @return
     *      The XP budgets for all the difficulties
     */
    public static List<Integer> buildXpBudgetForAllDifficulties(final List<Integer> partyLevels){
        Map<String, List<Integer>> thresholdMap = buildXpThresholdMap();
        List<Integer> thresholds = new ArrayList<>();
        int counter = 0;
        String difficulty = StringUtils.EMPTY;
        while (counter < 4) {
            Integer totalBudget = 0;
            if (counter == 0){
                difficulty = EASY;
            } else if (counter == 1){
                difficulty = MEDIUM;
            } else if (counter == 2){
                difficulty = HARD;
            } else if (counter == 3){
                difficulty = DEADLY;
            }
            for (Integer i : partyLevels) {
                totalBudget += thresholdMap.get(difficulty).get(i-1);
            }
            thresholds.add(totalBudget);
            counter++;
        }
        return thresholds;
    }

    /**
     * Calculator function to determine the encounter's difficulty by the monster's CR
     * @param monsterCRValues
     *      The number of monsters is the length of the array while their CR's are the elements
     * @return
     *      The total difficulty of the encounter by the CR value of the monsters using the Encounter Multiplier
     */
    public static Integer getEncounterDifficultyAsEXP(final List<Integer> monsterCRValues){
        Map<Double, Integer> CRMap = buildCRConversionMap();
        Double avgCR = 0.0;
        int xpSum = 0;
        for(Integer i : monsterCRValues){
            avgCR+= i;
        }
        avgCR/= monsterCRValues.size();
        avgCR = Math.floor(avgCR);

        Integer significantThreats = countSignificantThreats(avgCR, monsterCRValues);

        for(Integer i : monsterCRValues){
            xpSum = xpSum + CRMap.get(i.doubleValue());
        }

        xpSum*= setEncounterMultiplier(significantThreats);

        return xpSum;
    }

    /**
     * Calculator function to say what level encounter this will be for a particular party
     * @param monsterCRValues
     *      The group of monsters the party will be facing
     * @param partyLevels
     *      The party
     * @return
     *      The level of difficulty for the party as a String
     */
    public static String getEncounterDifficultyAsString(final List<Integer> monsterCRValues,
                                                      final List<Integer> partyLevels){
        Integer dc = getEncounterDifficultyAsEXP(monsterCRValues);
        Map<String, List<Integer>> thresholdMap = buildXpThresholdMap();
        Integer easyThreshold = 0;
        Integer mediumThreshold = 0;
        Integer hardThreshold = 0;
        Integer deadlyThreshold = 0;
        for(Integer i : partyLevels){
            easyThreshold = easyThreshold + thresholdMap.get(EASY).get(i);
            mediumThreshold = mediumThreshold + thresholdMap.get(MEDIUM).get(i);
            hardThreshold = hardThreshold + thresholdMap.get(HARD).get(i);
            deadlyThreshold = deadlyThreshold + thresholdMap.get(DEADLY).get(i);
        }
        if (dc <= easyThreshold){
            return EASY;
        } else if (dc <= mediumThreshold){
            return MEDIUM;
        } else if (dc <= hardThreshold){
            return HARD;
        } else if (dc <= deadlyThreshold){
            return DEADLY;
        } else{
            return "IDK";
        }
    }

    /**
     * Function to help determine the number of significant threats in an encounter
     * @param avgCR
     *      The Avg Challenge Rating of the all creatures in the encounter
     * @param monsterCRValues
     *      The specific encounter's monster's CR's
     * @return
     *      The number of monsters in the encounter considered a threat
     */
    private static Integer countSignificantThreats(Double avgCR, List<Integer> monsterCRValues){
        Integer significantThreats = 0;
        for(Integer i : monsterCRValues){
            if(i < 3){
                continue;
            }
            if (i-2 >= avgCR){
                significantThreats++;
            }
        }
        return significantThreats;
    }

    /**
     * Helper function to set the multiplier for the encounter
     * @param significantThreats
     *      The number of significant threats in the encounter
     * @return
     *      The relevant multiplier
     */
    private static Double setEncounterMultiplier(Integer significantThreats){
        Double encMultiplier;

        if (significantThreats > 15){
            encMultiplier = 4.0;
        } else{
            encMultiplier = createEncounterMultiplierMap().get(significantThreats);
        }

        return encMultiplier;
    }
}
