package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**armory at bottom of the screen. */
public class Armory {

    final int height = 130;
    ArrayList<DisplayTurret> inventory = new ArrayList<>();
    HashMap<String, Turret> turretTypes = new HashMap<>();
    String[] keyStrings;
    Player player;
    GamePanel gamePanel;
    int playerIndex = -1; //-1 if player not in armory

    /**constructor. */
    Armory(Player player, GamePanel gamePanel) {

        this.player = player;
        this.gamePanel = gamePanel;
        this.turretTypes = gamePanel.waveHandler.turretTypes;

        Set<String> possibleTurretsSet = turretTypes.keySet();
        keyStrings = possibleTurretsSet.toArray(new String[0]);
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

        if (waveIndex == 1) {
            Turret newTurret = turretTypes.get("basic").getCopy();
            DisplayTurret newDisplayTurret = new DisplayTurret(newTurret, newTurret.imagePathName);
            addTurret(newDisplayTurret);
            return;
        }

        for (int i = 0; i < Math.sqrt(waveIndex); i++) {
            int turretIndex = (int) Math.round(Math.random() * (turretTypes.size() - 1));
    
            Turret newTurret = turretTypes.get(keyStrings[turretIndex]).getCopy();
            DisplayTurret newDisplayTurret = new DisplayTurret(newTurret, newTurret.imagePathName);
            addTurret(newDisplayTurret);
        }

    }

    public void adjustPlayerIndex() {
        if (player.position.y < gamePanel.screenHeight - height) {
            playerIndex = -1;
            return;
        }
        if (inventory.isEmpty()) {
            playerIndex = -1;
            return;
        }

        int x = (int) (long) Math.round(player.position.x);
        int screenWidth = gamePanel.screenWidth;
        int chunkSize = screenWidth / inventory.size();

        playerIndex = (x - (x % chunkSize)) / chunkSize;
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

    /**draws the displayturrets on screen. */
    public void draw(Graphics2D g2) {

        for (DisplayTurret displayTurret : inventory) {
            displayTurret.draw(g2);
        }
    }
}
