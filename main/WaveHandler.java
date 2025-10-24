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
    TextReader textReader = new TextReader();

    HashMap<String, Enemy> enemyTypes = new HashMap<>();
    HashMap<String, Turret> turretTypes = new HashMap<>();
    ArrayList<Wave> waves = new ArrayList<>();

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
            enemyTypes = textReader.readEnemies(bufferedReader);

            bufferedReader = new BufferedReader(
                new FileReader("main\\text\\Waves.txt")
            );
            waves = textReader.readWaves(bufferedReader, enemyTypes, this);

            bufferedReader = new BufferedReader(
                new FileReader("main\\text\\turrets.txt")
            );
            turretTypes = textReader.readTurrets(bufferedReader);

        } catch (Exception e) {
            System.out.println("WaveHandler ");
            e.printStackTrace();
        }
    }

    /**spawns in enemies and starts the wave. */
    public HashSet<Enemy> startWave() {

        waveCounter++;
        if (waveCounter >= waves.size()) {

            //TODO: win the game
            gamePanel.gameState = 3; //endstate
            return new HashSet<>();
        }
        currentWave = waves.get(waveCounter);
        
        player.money += currentWave.income;

        for (Enemy enemy : currentWave.enemies) {
            enemy.position = new Vector(600 * Math.random(), 10);
            enemy.addDeathListener(gamePanel);
        }

        return currentWave.enemies;
    }
}