package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**Handles and draws all User Interface and Overlay related content. */
public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font headerTextFont;
    Font hintTextFont;
    Entity menuImage = new Entity();
    Entity healthBarFrame = new Entity();
    Entity healthBarPlate = new Entity();

    /**Constructor to include the gamePanel for g2 and screen size variations. */
    public UI(GamePanel parameterGamePanel) {
        this.gamePanel = parameterGamePanel;

        //FONTS
        headerTextFont = new Font("Arial", Font.BOLD, 50);
        hintTextFont = new Font("ArialRoundedMTBold", Font.BOLD, 20);
        

        //IMAGES
        try {
            menuImage.setBufferedImage(
                "/main/Images/menu.png",
                gamePanel.screenWidth,
                gamePanel.screenHeight
            );
            healthBarFrame.setBufferedImage(
                "/main/Images/healthbarframe.png",
                200,
                30
            );
            healthBarPlate.setBufferedImage(
                "/main/Images/healthbarplate.png",
                200,
                30
            );
        } catch (Exception e) {
            System.out.println("couldnt render image error UI/MENUIMAGE");
            e.printStackTrace();
            System.out.println(healthBarPlate.imagePathName);
        }

        menuImage.position = gamePanel.screenCenter;
        healthBarFrame.position = new Vector(
            450,
            50);
        healthBarPlate.position = new Vector(
            450,
            50);

    }

    /**Draws UI elements based off gamePanel.gameState. */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(headerTextFont);
        g2.setColor(Color.white);
        
        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayScreen();
        }
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
        if (gamePanel.gameState == gamePanel.menuState) {
            drawMenuScreen();
        }
        if (gamePanel.gameState == gamePanel.endState) {
            drawEndScreen();
        }
    }

    /**Draws UI for playState gameState. */
    public void drawPlayScreen() {

        //DRAW WAVE COUNTER
        String waveCounterText = "Wave: " + gamePanel.waveHandler.waveCounter;

        g2.drawString(waveCounterText, 30, 50);


        //DRAW PLAYER HEALTH
        healthBarPlate.draw(g2);
        setHealthColor(gamePanel.player.health);
        g2.fillRect(
            355,
            40,
            190 * gamePanel.player.health / 100,
            20);
        healthBarFrame.draw(g2);


        //DRAW Tip for picking up towers
        String pickTowerHintText = "Press [E] to buy turret";
        g2.setColor(Color.white);
        g2.setFont(hintTextFont);
        gamePanel.armory.adjustPlayerIndex();
        if (!gamePanel.player.hand.handsFull) {
            if (gamePanel.armory.turretHovered != null) {
                g2.drawString(
                    pickTowerHintText, 
                    Math.round(gamePanel.player.position.x) + 30, 
                    Math.round(gamePanel.player.position.y)
                );
            }
        }
        
    }

    /**Draws UI for pauseState gameState. */
    public void drawPauseScreen() {

        String text = "PAUSED";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        g2.drawString(
            text, 
            Math.round(gamePanel.screenCenter.x - textWidth / 2),
            Math.round(gamePanel.screenCenter.y)
        );
    }

    /**Draws UI for menuState gameState. */
    public void drawMenuScreen() {

        String text = "Press [Space] to Play!";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        menuImage.draw(this.g2);
        g2.drawString(
            text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
            Math.round(gamePanel.screenCenter.y)
        );
    }

    /**Draws UI for endScreen gameState or in other words, when player dies. */
    public void drawEndScreen() {
        String text = "You died! Game Over";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        menuImage.draw(this.g2);
        g2.drawString(
            text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
            Math.round(gamePanel.screenCenter.y)
        );
    }

    /**Sets g2 color based on health amount. */
    public void setHealthColor(int health) {

        if (health < 25) {
            g2.setColor(Color.red);
        } else if (health < 50) {
            g2.setColor(Color.orange);
        } else if (health < 75) {
            g2.setColor(Color.yellow);
        } else {
            g2.setColor(Color.green);
        }
    }
}
