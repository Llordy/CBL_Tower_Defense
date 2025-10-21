package main;

import java.util.ArrayList;
import java.util.HashSet;

/**armory at bottom of the screen. */
public class Armory {

    int height = 200;
    ArrayList<Turret> inventory = new ArrayList<Turret>();
    Player player;
    GamePanel gamePanel;

    /**constructor. */
    Armory(Player player, GamePanel gamePanel) {

        this.player = player;
        this.gamePanel = gamePanel;
        addTurret(
            new Turret(
                new Attack[] {new Attack(10, 50, 1)},
                100,
                3,
                70,
                70,
                new HashSet<HealthEntity>(gamePanel.enemies)
            )
        );
    }

    private void addTurret(Turret turret) {
        inventory.add(turret);
    }

    /**ran every end of wave. */
    public void restock(int waveIndex) {

        Turret turret;

        for (int i = 0; i < Math.sqrt(waveIndex); i++) {

            turret = new Turret(
                new Attack[] {new Attack(10, 50, 1)},
                100,
                3,
                70,
                70,
                new HashSet<HealthEntity>(gamePanel.enemies)
            );
            
            addTurret(turret);
        }
    }

    /**have the player buy a turret. */
    public void buyTurret(int index) {

        if (inventory.size() == 0) {
            return;
        }

        Turret turret = inventory.get(index);

        if (player.money < turret.cost) {
            return;
        }
        if (player.hand.handsFull) {
            return;
        }

        player.money -= turret.cost;
        player.hand.grabTurret(turret);

        inventory.remove(turret);
    }
}
