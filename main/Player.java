package main;

import java.util.ArrayList;

/** the player, should only be 1 of. */
public class Player extends HealthEntity {

    int money = 0;
    double speed;
    KeyHandler keyHandler;

    public ArrayList<DeathListener> listeners = new ArrayList<DeathListener>();

    /**constructor. */
    public Player(Vector position, float speed, int width, int height, int health) {

        this.health = health;
        this.maxHealth = health;
        this.position = position;
        this.speed = speed;
        try {
            setBufferedImage("/main/Images/builderBoi.png", width, height);
        } catch (Exception e) {
            System.out.println("couldnt render image error PLAYER");
        }
    }

    /**moves the player based on delta and current keys pressed. */
    public void update(double delta) {

        //movement
        Vector movement = new Vector(0, 0);
        double displacement = speed * delta;
        if (keyHandler.upPressed) {
            movement.y -= displacement;
        }

        if (keyHandler.downPressed) {
            movement.y += displacement;
        }

        if (keyHandler.leftPressed) {
            movement.x -= displacement;
        }

        if (keyHandler.rightPressed) {
            movement.x += displacement;
        }

        //remove diagonal boost
        if (!(movement.y == 0 || movement.x == 0)) {

            movement = movement.divide(Math.sqrt(2));
        }

        position.x += movement.x;
        position.y += movement.y;

        //Boundary check
        checkBoundaries(position);
    }

    /**you die, skill issue.
     * will call gamepanel, which in turn handles the deathscreen and such.
    */
    @Override
    void die() {
        for (DeathListener listener : listeners) {
            listener.EntityDied("Player", 0);
        }
    }

    /**
     * adds a listener to be called up death.
     */
    @Override
    void addDeathListener(DeathListener toAdd) {
        listeners.add(toAdd);
    }
}
