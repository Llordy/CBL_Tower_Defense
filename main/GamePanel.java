package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

/**The big file, from here the game is run. */
public class GamePanel extends JPanel implements Runnable {

    //FPS
    double maxFPS = 60.0d;
    double minFrameTime = 1d / maxFPS;


    //SYSTEM
    public final int originalTileSize = 32; // 32x32 tiles
    public final int screenWidth = 600;
    public final int screenHeight = 800;
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    //BACKGROUND
    Entity background = setBackground();
    

    //ENTITY AND OBJECT
    Player player = new Player(
        new Vector(400, 400),
        150,
        40,
        40,
        10
    );
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<Turret> turrets = new ArrayList<Turret>();
    WaveHandler waveHandler = new WaveHandler(player);

    //PAINTCOMPONENT
    ArrayList<Enemy> savedEnemies;

    //GAME STATES
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    
    /**initializes the background. */
    public Entity setBackground() {
        Entity background = new Entity();
        background.position = new Vector(screenWidth / 2, screenHeight / 2);
        try {
            background.setBufferedImage(
                "/main/Images/CBL_background.png",
                screenWidth,
                screenHeight
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return background;

    }

    /**initializes the window. */
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        player.keyHandler = keyHandler;
        this.setFocusable(true);

        //player.setBufferedImage(builderBoi);
    }

    /**starts the game loop. */
    public void startGameThread() {

        gameState = playState;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**DO NOT TOUCH, this runs the entire delta time system. */
    @Override
    public void run() {

        double delta = 0;
        double lastTime = System.currentTimeMillis() / 1000d;
        double currentTime;

        //gameloop
        while (gameThread != null) {
            currentTime = System.currentTimeMillis() / 1000d;
            delta += currentTime - lastTime;

            if (delta >= minFrameTime) {

                mainBody(delta);

                delta -= minFrameTime;
            }
            
            lastTime = currentTime;
        }
    }

    /**main body that runs every frame. */
    void mainBody(double delta) {
        // 1 UPDATE: update information such as positions
        update(delta);

        // 2 DRAW: draw on the screen with updated information
        repaint();
    }
    
    /**updates the game every frame. */
    public void update(double delta) {

        if (gameState == playState) {
            //PLAYER
            player.update(delta);

            //ENEMIES
            for (Enemy enemy : enemies) {
                enemy.update(delta, turrets, player);
            }

            //TURRETS
            for (Turret turret : turrets) {
                turret.update(delta, enemies);
            }

            //WAVEHANDLER
            enemies.addAll(waveHandler.run(delta));
        }

        if (gameState == pauseState) {
            //nothing
        }
    }

    /**paints a test rectangle. */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        
        background.draw(g2);
        player.draw(g2);

        savedEnemies = enemies;

        for (Enemy enemy : savedEnemies) {
            enemy.draw(g2);
        }

        g2.dispose();
    }
}
