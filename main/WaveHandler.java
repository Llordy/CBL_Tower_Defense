package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**handles the wave system. */
public class WaveHandler {

    Wave currentWave;
    Player player;
    GamePanel gamePanel;
    int waveCounter = 0;
    WaveSetter waveSetter = new WaveSetter();

    HashMap<String, Enemy> enemyTypes = new HashMap<>();
    ArrayList<Wave> waves = new ArrayList<>();

    //TODO: implement different enemy types?

    /**the class describing a wave of enemies. */
    public class Wave {

        HashSet<Enemy> enemies;
        int income;

        /**initializes a wave filled with defaultLad.*/
        Wave(HashSet<Enemy> enemies, int income) {

            this.enemies = enemies;
            this.income = income;
        }
    }

    /**sets defaultLad and the waves. */
    WaveHandler(Player player, GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.player = player;

        try {
            BufferedReader bufferedReader = new BufferedReader(
                new FileReader("main\\text\\Enemies.txt")
            );
            enemyTypes = waveSetter.readEnemies(bufferedReader);

            bufferedReader = new BufferedReader(
                new FileReader("main\\text\\Waves.txt")
            );
            waves = waveSetter.readWaves(bufferedReader, enemyTypes, this);

        } catch (Exception e) {
            System.out.println("WaveHandler " + e);

            for (int i = 0; i < e.getStackTrace().length; i++) {
                System.out.println(e.getStackTrace()[i]);
            }
        }
    }

    

    /**spawns in enemies and starts the wave. */
    public HashSet<Enemy> startWave() {

        waveCounter++;
        currentWave = waves.get(waveCounter);
        
        player.money += currentWave.income;

        for (Enemy enemy : currentWave.enemies) {
            enemy.position = new Vector(600 * Math.random(), 10);
        }

        return currentWave.enemies;
    }
}