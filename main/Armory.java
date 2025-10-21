package main;

import java.util.ArrayList;
import java.util.HashSet;

/**armory at bottom of the screen. */
public class Armory {

    int height = 200;
    ArrayList<DisplayTurret> inventory = new ArrayList<>();
    Player player;
    GamePanel gamePanel;

    /**constructor. */
    Armory(Player player, GamePanel gamePanel) {

        this.player = player;
        this.gamePanel = gamePanel;

        Turret turret = new Turret(
            new Attack[] {new Attack(10, 50, 1)},
            100,
            3,
            70,
            70,
            new HashSet<HealthEntity>(gamePanel.enemies)
        );

        addTurret(new DisplayTurret(turret, turret.imagePathName));
    }

    private class DisplayTurret extends Entity {

        Turret turret;

        DisplayTurret(Turret turret, String imagePathName) {

            this.turret = turret;
            try {
                setBufferedImage(imagePathName, turret.width, turret.height);
            } catch (Exception e) {
                System.out.println("couldnt render image error DISPLAYTURRET");
            }
        }
    }

    private void addTurret(DisplayTurret displayTurret) {
        inventory.add(displayTurret);
    }

    /**ran every end of wave. */
    public void restock(int waveIndex) {

        DisplayTurret displayTurret;
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

            displayTurret = new DisplayTurret(turret, turret.imagePathName);
            
            addTurret(displayTurret);
        }
    }

    /**have the player buy a turret. */
    public void buyTurret(int index) {

        if (inventory.size() == 0) {
            return;
        }

        Turret turret = inventory.get(index).turret;

        if (player.money < turret.cost) {
            return;
        }
        if (player.hand.handsFull) {
            return;
        }

        player.money -= turret.cost;
        player.hand.grabTurret(turret);

        inventory.remove(index);
    }
}
