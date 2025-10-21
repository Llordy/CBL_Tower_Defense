package main;

import java.util.HashSet;
import java.util.Stack;

/**handles the wave system. */
public class WaveHandler {

    Wave currentWave;
    Player player;
    boolean startingWave = true;
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

                this.enemies.add(
                    new Enemy(
                        "/main/Images/enemy.png",
                        50,
                        50,
                        30,
                        new Attack(10, 30, 1)
                    )
                );
            }
        }
    }

    /**sets defaultLad and the waves. */
    WaveHandler(Player player) {

        this.player = player;
        waves = new Stack<Wave>();

        

        int[] waveSizes = {3, 6, 10};

        //initializes the waves
        
        for (int i = waveSizes.length - 1; i >= 0; i--) {

            Wave newWave = new Wave(waveSizes[i], i);
            waves.add(newWave);
        }
    }

    /**spawns in enemies and starts the wave. */
    public HashSet<Enemy> startWave(Wave wave) {

        System.out.println("weewoo wave starting " + waveCounter);

        waveCounter++;
        player.money += wave.income;

        System.out.println(waveCounter);

        for (Enemy enemy : wave.enemies) {
            enemy.position = new Vector(600 * Math.random(), 10);
        }

        return wave.enemies;
    }
        

    public HashSet<Enemy> run(double delta) {

        System.out.println(waveCounter);

        if (startingWave) {
            currentWave = waves.pop();
            player.money += currentWave.income;
            startingWave = false;
            
            return startWave(currentWave);

        } else {
            return new HashSet<Enemy>(0);
        }
    }
}