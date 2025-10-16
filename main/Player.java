package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** the player, should only be 1 of. */
public class Player extends HealthEntity implements ActionListener {

    int money = 0;
    double speed;
    KeyHandler keyHandler;

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

    /**you die, skill issue. */
    void die() {
        System.out.println("man im dead");
        //TODO: death screen etc
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
