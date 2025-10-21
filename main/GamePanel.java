package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JPanel;

/**The big file, from here the game is run. */
public class GamePanel extends JPanel 
    implements Runnable, DeathListener {

    //FPS
    double maxFPS = 60.0d;
    double minFrameTime = 1d / maxFPS;

    //SYSTEM
    public final int originalTileSize = 32; // 32x32 tiles
    public final int screenWidth = 600;
    public final int screenHeight = 800;
    public final Vector screenCenter = new Vector(screenWidth / 2, screenHeight / 2);
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    //BACKGROUND
    Entity background = setBackground();
    
    //UI
    UI userInterface = new UI(this);


    //ENTITY AND OBJECT
    Player player = new Player(
        new Vector(400, 400),
        150,
        40,
        40,
        100,
        this
    );
    HashSet<Enemy> enemies = new HashSet<>();
    HashSet<Turret> turrets = new HashSet<>();
    WaveHandler waveHandler = new WaveHandler(player, this);
    Armory armory = new Armory(player, this);

    //PAINTCOMPONENT
    HashSet<Enemy> savedEnemies;
    HashSet<Turret> savedTurrets;

    //GAME STATES
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int endState = 3;
    
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

        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * runs once on startup.
     */
    public void startupCode() {
        player.addDeathListener(this);
        player.armoryY = screenHeight - armory.height;
        gameState = menuState;
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
            if (enemies.size() == 0) {
                enemies.addAll(waveHandler.startWave());
                armory.restock(waveHandler.waveCounter);
            }
            for (Enemy enemy : enemies) {
                enemy.update(delta, turrets, player);
            }

            //TURRETS
            for (Turret turret : turrets) {
                turret.update(delta, new HashSet<HealthEntity>(enemies));
            }
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

        //Background
        background.draw(g2);

        //Enemies
        savedEnemies = enemies;
        for (Enemy enemy : savedEnemies) {
            enemy.draw(g2);
        }

        //Turrets
        savedTurrets = turrets;
        for (Turret turret : savedTurrets) {
            turret.draw(g2);
        }

        //Armory
        armory.draw(g2);

        //Player
        player.draw(g2);

        //UI
        userInterface.draw(g2);

        g2.dispose();
    }

    @Override
    public void entityDied(String classID, Entity deadEntity) {

        switch (classID) {
            case "Player" -> {
                System.out.println("player died");
                gameState = endState;
            }

            case "Turret" -> {
                System.out.println("turret died");
                turrets.remove(deadEntity);
            }

            case "Enemy" -> {
                enemies.remove(deadEntity);
            }

            default -> {
                System.out.println(classID + " is an unknown entity that has died");
            }
        }
    }
}
