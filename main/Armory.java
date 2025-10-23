package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;

/**armory at bottom of the screen. */
public class Armory {

    final int height = 130;
    private ArrayList<DisplayTurret> inventory = new ArrayList<>();
    Player player;
    GamePanel gamePanel;

    /**constructor. */
    Armory(Player player, GamePanel gamePanel) {

        this.player = player;
        this.gamePanel = gamePanel;
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

    private void addTurret(DisplayTurret newDisplayTurret) {
        inventory.add(newDisplayTurret);
        newDisplayTurret.position = new Vector(0, 0); //to avoid errors from null vector

        int screenWidth = gamePanel.screenWidth;
        int chunkSize = screenWidth / inventory.size();

        //set X of position
        for (int i = 0; i < inventory.size(); i++) {
            inventory.get(i).position.x = 0.5 * chunkSize + i * chunkSize;
        }

        //set Y of position
        newDisplayTurret.position.y = gamePanel.screenHeight - height * 0.5;
    }

    private void removeTurret(int index) {
        inventory.remove(index);

        if (inventory.size() == 0) {
            return;
        }

        int screenWidth = gamePanel.screenWidth;
        int chunkSize = screenWidth / inventory.size();

        //shift X positions
        for (int i = 0; i < inventory.size(); i++) {
            inventory.get(i).position.x = 0.5 * chunkSize + i * chunkSize;
        }
    }

    /**ran every end of wave. */
    public void restock(int waveIndex) {

        for (int i = 0; i < Math.sqrt(waveIndex); i++) {
            addJeb();
        }
    }

    /**have the player buy a turret. */
    public void buyTurret(double posX) {

        if (inventory.size() == 0 || player.hand.handsFull) {
            return;
        }

        //divides the armory up into horizontal chunks for each
        int x = (int) (long) Math.round(posX);
        int screenWidth = gamePanel.screenWidth;
        int chunkSize = screenWidth / inventory.size();
        int index = (x - (x % chunkSize)) / chunkSize;

        Turret turret = inventory.get(index).turret;

        if (player.money < turret.cost) {
            return;
        }

        player.money -= turret.cost;
        player.hand.grabTurret(turret);

        removeTurret(index);
    }

    /**semi-placeholder for a simple turret. */
    public void addJeb() {
        Turret jeb = new Turret(
                new Attack[] {new Attack(40, 200, 1000)},
                100,
                3,
                70,
                70,
                new HashSet<HealthEntity>(gamePanel.enemies)
            );

        DisplayTurret displayJeb = new DisplayTurret(jeb, jeb.imagePathName);
            
        addTurret(displayJeb);
    }

    /**draws the displayturrets on screen. */
    public void draw(Graphics2D g2) {

        for (DisplayTurret displayTurret : inventory) {
            displayTurret.draw(g2);
        }
    }
}
