package main;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import main.WaveHandler.Wave;

public class WaveSetter {

    /**read the enemies from Enemies.txt */
    public HashMap<String, Enemy> readEnemies(BufferedReader bufferedReader) {
        
        String enemyString;
        String[] enemyStringArray;
        String[] attackStringArray;
        HashMap<String, Enemy> enemyTypes = new HashMap<>();

        try {
            //read every enemy
            while ((enemyString = bufferedReader.readLine()) != null) {

                //split the enemy string
                enemyStringArray = enemyString.split(" ");

                //read and split the attack string
                attackStringArray = bufferedReader.readLine().split(" ");

                //construct the enemy
                Enemy enemy = new Enemy(
                    enemyStringArray[1],
                    Integer.valueOf(enemyStringArray[2]),
                    Integer.valueOf(enemyStringArray[3]),
                    Double.valueOf(enemyStringArray[4]),
                    Integer.valueOf(enemyStringArray[5]),
                    new Attack(
                        Integer.valueOf(attackStringArray[0]),
                        Integer.valueOf(attackStringArray[1]),
                        Integer.valueOf(attackStringArray[2])
                    )
                );

                //add the enemy and key to the hashmap
                enemyTypes.put(enemyStringArray[0], enemy);
            }

            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("readEnemies" + e);

            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i]);
            }
        }

        return enemyTypes;
    }

    /**reads the waves from Waves.txt */
    public ArrayList<Wave> readWaves(BufferedReader bufferedReader, HashMap<String, Enemy> enemyTypes, WaveHandler waveHandler) {

        String waveString;
        String[] waveStringArray = null;
        ArrayList<Wave> waves = new ArrayList<>();

        HashSet<Enemy> enemies = new HashSet<>();

        try {
            int waveNumber = 0;

            //read every wave
            while ((waveString = bufferedReader.readLine()) != null) {

                //split the wave string
                waveStringArray = waveString.split(" ");

                int income = Integer.valueOf(waveStringArray[2]);

                //read the list of enemies
                enemies = getEnemies(bufferedReader, waveStringArray, enemyTypes);

                //construct the wave
                Wave wave = waveHandler.new Wave(enemies, income);

                //add the enemy and key to the hashmap
                waves.add(waveNumber, wave);

                waveNumber++;
            }

            

            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("readWaves " + e);

            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i]);
            }
        }

        return waves;
    }

    private HashSet<Enemy> getEnemies(BufferedReader bufferedReader, String[] waveStringArray, HashMap<String, Enemy> enemyTypes) {

        String[] enemiesStringArray;
        HashSet<Enemy> outputEnemies = new HashSet<>();

        try {

            for (int i = 0; i < Integer.valueOf(waveStringArray[1]); i++) {

                //read the enemy string and split it
                enemiesStringArray = bufferedReader.readLine().split(" ");

                //set variables for readability
                int amount = Integer.valueOf(enemiesStringArray[1]);
                String enemyType = enemiesStringArray[0];

                Enemy enemy = enemyTypes.get(enemyType);

                //paste enemy given amount of times into enemies
                for (int j = 0; j < amount; j++) {
                    outputEnemies.add(enemy.getCopy());
                }

                System.out.println(amount);
            }
            
        } catch (Exception e) {
            System.out.println("getEnemies" + e);

            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i]);
            }
        }

        System.out.println(outputEnemies.size());
        return outputEnemies;
    }
}
