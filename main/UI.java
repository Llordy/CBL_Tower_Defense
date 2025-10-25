package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import main.Armory.DisplayTurret;

/**Handles and draws all User Interface and Overlay related content. */
public class UI {
    
    GamePanel gamePanel;
    Graphics2D g2;
    Font headerTextFont;
    Font mediumTextFont;
    Font smallTextFont;
    Entity menuImage = new Entity();
    Entity healthBarFrame = new Entity();
    Entity healthBarPlate = new Entity();
    Entity enemyHealthBarFrame = new Entity();
    Entity enemyHealthBarPlate = new Entity();
    String endGameText;

    /**Constructor to include the gamePanel for g2 and screen size variations. */
    public UI(GamePanel parameterGamePanel) {
        this.gamePanel = parameterGamePanel;

        //FONTS
        headerTextFont = new Font("Arial", Font.BOLD, 50);
        mediumTextFont = new Font("fontArialRounded", Font.PLAIN, 20);
        smallTextFont = new Font("fontArialRounded", Font.BOLD, 10);
        

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
            enemyHealthBarFrame.setBufferedImage(
                "/main/Images/healthbarframe.png",
                30,
                10
            );
            enemyHealthBarPlate.setBufferedImage(
                "/main/Images/healthbarplate.png",
                30,
                10
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

        //DRAW MONEY COUNTER
        String moneyCounterText = "Money: " + gamePanel.player.money;
        g2.setFont(mediumTextFont);
        g2.drawString(moneyCounterText, 10, 80);

        //DRAW PLAYER HEALTH
        healthBarPlate.draw(g2);
        setHealthColor(gamePanel.player);
        g2.fillRect(
            355,
            40,
            190 * gamePanel.player.health / 100,
            20);
        healthBarFrame.draw(g2);

        //DRAW ENEMY HEALTH BARS
        for (Enemy enemy : gamePanel.enemies) {
            setHealthColor(enemy);
            g2.fillRect(
                (int) (enemy.position.x) - enemy.width / 2,
                (int) (enemy.position.y) - enemy.height / 2 - 8,
                enemy.width,
                5);
            
        }

        //DRAW Tip for picking up towers
        g2.setColor(Color.white);
        g2.setFont(smallTextFont);
        gamePanel.armory.adjustPlayerIndex();

        if (!gamePanel.player.hand.handsFull) {

            if (gamePanel.armory.playerIndex != -1) {
                int index = gamePanel.armory.playerIndex;
                DisplayTurret displayTurret = gamePanel.armory.inventory.get(index);
                String pickTowerHintText = "Press [E] to buy turret";

                String firerateString = String.valueOf(1000.0d / displayTurret.turret.attacks[0].fireDelay);
                if (firerateString.length() > 5) {
                    firerateString = firerateString.substring(0,5);
                }

                String[] turretInfoStrings = new String[] {
                    pickTowerHintText,
                    "cost: " + displayTurret.turret.cost,
                    "health: " + displayTurret.turret.health,
                    "damage: " + displayTurret.turret.attacks[0].damage * displayTurret.turret.attacks.length,
                    "range: " + displayTurret.turret.attacks[0].range,
                    "shots per second: " + firerateString
                };

                long x = Math.round(displayTurret.position.x);
                long y = Math.round(displayTurret.position.y);

                int offsetX = -50;
                int offsetY = -40;

                for (int i = 0; i < turretInfoStrings.length; i++) {
                    
                    g2.drawString(
                    turretInfoStrings[i],
                    x + offsetX,
                    y + offsetY
                    );

                    offsetY += 10;
                }
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
        if (gamePanel.player.health <= 0) {
            endGameText = "You died! Game Over";
        } else {
            endGameText = "You win! Game Over";
        }
        int textWidth = (int) g2.getFontMetrics().getStringBounds(endGameText, g2).getWidth();
        menuImage.draw(this.g2);
        g2.drawString(
            endGameText, Math.round(gamePanel.screenCenter.x - textWidth / 2),
            Math.round(gamePanel.screenCenter.y)
        );
    }

    /**Sets g2 color based on health amount. */
    public void setHealthColor(HealthEntity entity) {
        int health = entity.health;
        int maxHealth = entity.maxHealth;
        if (health < maxHealth * 0.25) {
            g2.setColor(Color.red);
        } else if (health < maxHealth * 0.50) {
            g2.setColor(Color.orange);
        } else if (health < maxHealth * 0.75) {
            g2.setColor(Color.yellow);
        } else {
            g2.setColor(Color.green);
        }
    }
}
