package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

/**Handles and draws all User Interface and Overlay related content. */
public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font fontArialPlain;
    Entity menuImage = new Entity();
    Entity healthBarFrame = new Entity();

    /**Constructor to include the gamePanel for g2 and screen size variations. */
    public UI(GamePanel parameterGamePanel) {
        this.gamePanel = parameterGamePanel;

        //FONTS
        fontArialPlain = new Font("Arial", Font.BOLD, 50);

        //IMAGES
        try {
            menuImage.setBufferedImage(
                "/main/Images/menu.png",
                gamePanel.screenWidth,
                gamePanel.screenHeight
            );
            healthBarFrame.setBufferedImage(
                "/main/Images/healthbarframe.png",
                gamePanel.screenWidth / 4,
                gamePanel.screenHeight / 24
            );
        } catch (IOException e) {
            System.out.println("couldnt render image error UI/MENUIMAGE");
        }

        menuImage.position = gamePanel.screenCenter;
        healthBarFrame.position = new Vector(
            gamePanel.screenWidth / 4 * 3,
            50);

    }

    /**Draws UI elements based off gamePanel.gameState. */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(fontArialPlain);
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

        //TODO: DRAW UI FOR PLAYSTATE INCLUDING WAVE COUNTER AND PLAYER HEALTH

        //DRAW WAVE COUNTER
        String waveCounterText = "Wave: " + gamePanel.waveHandler.waveCounter;

        g2.drawString(waveCounterText, 30, 50);


        //DRAW PLAYER HEALTH
        healthBarFrame.draw(g2);
        drawPlayerHealth();
        

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
        g2.drawString(text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
                             Math.round(gamePanel.screenCenter.y));
    }


    /**Draws UI for endScreen gameState or in other words, when player dies. */
    public void drawEndScreen() {
        String text = "You died! Game Over";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        menuImage.draw(this.g2);
        g2.drawString(text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
                             Math.round(gamePanel.screenCenter.y));
    }

    public void drawPlayerHealth() {
        g2.setColor(Color.GREEN);
        g2.fillRect(gamePanel.screenWidth / 4 * 3,
            50, 100, 50);
    }
}
