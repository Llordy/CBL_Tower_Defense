package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**handles the wave system. */
public class WaveHandler {

    Wave currentWave;
    Player player;
    GamePanel gamePanel;
    Stack<Wave> waves;
    int waveCounter = 0;

    HashMap<String, Enemy> enemyTypes = new HashMap<>();

    

    //TODO: implement different enemy types?

    /**the class describing a wave of enemies. */
    class Wave {

        HashSet<Enemy> enemies;
        int income;

        /**initializes a wave filled with defaultLad.*/
        Wave(int waveSize, int income) {

            this.enemies = new HashSet<Enemy>(waveSize);
            this.income = income;

            if (waveSize == 0) {
                return;
            }

            for (int i = 0; i < waveSize; i++) {

                Enemy newEnemy = new EnemyGrunt();
                newEnemy.addDeathListener(gamePanel);
                this.enemies.add(newEnemy);
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(
                    new FileReader("main\\Waves.txt")
                );
                
                String str;

                ArrayList<String> enemyString = new ArrayList<String>();

                if ((str = bufferedReader.readLine()) != null) {
                    enemyString.add(str);
                } else {

                    //TODO: handle end of all waves

                    bufferedReader.close();
                }

                

                for (String s : enemyString) {
                    //TODO: turn into enemies
                }
    
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**sets defaultLad and the waves. */
    WaveHandler(Player player, GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.player = player;

        try {
            BufferedReader bufferedReader = new BufferedReader(
                new FileReader("main\\Enemies.txt")
            );

            enemyTypes = readEnemies(bufferedReader);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //read all enemies into enemyTypes
    private HashMap<String, Enemy> readEnemies(BufferedReader bufferedReader) {
        
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
                    Integer.valueOf(enemyStringArray[4]),
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
            System.out.println(e);
        }

        return enemyTypes;
    }

    private Wave getWave(int waveIndex) {
        return new Wave(
            waveIndex * 3,
            waveIndex
            );
    }

    /**spawns in enemies and starts the wave. */
    public HashSet<Enemy> startWave() {

        waveCounter++;
        currentWave = getWave(waveCounter);
        
        player.money += currentWave.income;

        for (Enemy enemy : currentWave.enemies) {
            enemy.position = new Vector(600 * Math.random(), 10);
        }

        return currentWave.enemies;
    }
}