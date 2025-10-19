package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font fontArialPlain;
    BufferedImage menuImage = null;


    public UI(GamePanel parameterGamePanel) {
        this.gamePanel = parameterGamePanel;

        //FONTS
        fontArialPlain = new Font("Arial", Font.BOLD, 80);

        //IMAGES

        try {
            menuImage = ImageIO.read(new File("/main/Images/menu.png"));
        } catch (IOException e) {
            System.out.println("couldnt render image error UI/MENUIMAGE");
        }

    }

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
            //TODO: DRAW UI FOR MAIN MENU
        }
    }

    public void drawPlayScreen() {
        //TODO: DRAW UI FOR PLAYSTATE INCLUDING WAVE COUNTER AND PLAYER HEALTH
        String waveCounterText = "Wave: " + gamePanel.waveHandler.waves.size();

        g2.drawString(waveCounterText, 0, 80);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();


        g2.drawString(text, Math.round(gamePanel.screenCenter.x - textLength / 2),
                             Math.round(gamePanel.screenCenter.y));
    }

    public void drawMenuScreen() {
        g2.drawImage(menuImage, 0, 0, null);
    }
}
