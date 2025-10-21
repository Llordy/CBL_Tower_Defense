package main;

import java.util.HashSet;
import java.util.Stack;

/**handles the wave system. */
public class WaveHandler {

    Wave currentWave;
    Player player;
    GamePanel gamePanel;
    Stack<Wave> waves;
    int waveCounter = 0;

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
        }
    }

    /**sets defaultLad and the waves. */
    WaveHandler(Player player, GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.player = player;
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