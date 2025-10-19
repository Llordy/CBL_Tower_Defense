package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;


public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font fontArialPlain;
    Entity menuImage = new Entity();


    public UI(GamePanel parameterGamePanel) {
        this.gamePanel = parameterGamePanel;

        //FONTS
        fontArialPlain = new Font("Arial", Font.BOLD, 80);

        //IMAGES
        try {
            menuImage.setBufferedImage("/main/Images/menu.png", gamePanel.screenWidth, gamePanel.screenHeight);
        } catch (IOException e) {
            System.out.println("couldnt render image error UI/MENUIMAGE");
        }
        menuImage.position = gamePanel.screenCenter;

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
    }

    public void drawPlayScreen() {

        //TODO: DRAW UI FOR PLAYSTATE INCLUDING WAVE COUNTER AND PLAYER HEALTH
        String waveCounterText = "Wave: " + gamePanel.waveHandler.waves.size();

        g2.drawString(waveCounterText, 0, 80);
    }

    public void drawPauseScreen() {

        String text = "PAUSED";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();


        g2.drawString(text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
                             Math.round(gamePanel.screenCenter.y));
    }

    public void drawMenuScreen() {

        String text = "Play";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        menuImage.draw(this.g2);
        g2.drawString(text, Math.round(gamePanel.screenCenter.x - textWidth / 2),
                             Math.round(gamePanel.screenCenter.y));
    }
}
