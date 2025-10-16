package main;

import java.util.ArrayList;
import java.util.Stack;

/**handles the wave system. */
public class WaveHandler {

    Wave currentWave;
    Player player;
    boolean startingWave = true;
    Stack<Wave> waves;

    //TODO: implement different enemy types?

    /**the class describing a wave of enemies. */
    class Wave {

        ArrayList<Enemy> enemies;
        int income;

        /**initializes a wave filled with defaultLad.*/
        Wave(int waveSize, int income) {

            this.enemies = new ArrayList<Enemy>(waveSize);
            this.income = income;

            if (waveSize == 0) {
                return;
            }

            for (int i = 0; i < waveSize; i++) {

                this.enemies.add(
                    i,
                    new Enemy(
                        "/main/Images/enemy.png",
                        50,
                        50,
                        30,
                        new Attack(10, 100, 1)
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
    public ArrayList<Enemy> startWave(Wave wave) {

        player.money += wave.income;

        for (Enemy enemy : wave.enemies) {
            enemy.position = new Vector(600 * Math.random(), 10);
        }

        return wave.enemies;
    }
        

    public ArrayList<Enemy> run(double delta) {
        if (startingWave) {
            currentWave = waves.pop();
            player.money += currentWave.income;
            startingWave = false;
            return startWave(currentWave);
        } else {
            return new ArrayList<Enemy>(0);
        }
    }
}