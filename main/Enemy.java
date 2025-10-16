package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

/**default class for each enemy.
 */
public class Enemy extends HealthEntity {
    
    BufferedImage image;
    double speed;
    HealthEntity currentTarget;
    ArrayList<HealthEntity> savedTargets;
    Attack attack;

    /**constructor. */
    Enemy(String pathName, int width, int height, double speed, Attack attack) {
        try {
            setBufferedImage(pathName, width, height);
        } catch (Exception e) {
            System.out.println("couldnt render image error ENEMY");
        }
        
        this.speed = speed;
        this.attack = attack;
    }

    /**returns the priority target.
     * currently distances based
    */
    private HealthEntity decideTarget(Collection<HealthEntity> healthEntities) {
        
        int minDistance = 1000000;
        HealthEntity minTarget = null;

        for (HealthEntity target : healthEntities) {

            if (this.distanceTo(target) < minDistance) {

                minTarget = target;
            }
        }

        return minTarget;
    }

    /**updates every frame. */
    public void update(double delta, ArrayList<Turret> turrets, Player player) {

        savedTargets = new ArrayList<HealthEntity>(turrets);
        savedTargets.add(player);

        currentTarget = decideTarget(savedTargets);

        moveTowards(currentTarget, delta);

        

        if (this.distanceTo(currentTarget) <= attack.range) {

            attack.perform(currentTarget);
        }
    }

    /**moves the enemy towards the given entity. 
     * TODO: collisions
    */
    public void moveTowards(Entity target, double delta) {

        if (distanceTo(target)  <= attack.range) {

            //TODO: go into target until collision

        } else {

            //moves towards the target, not checking collisions
            Vector deltaPos = target.position.subtract(this.position);
            deltaPos = deltaPos.divide((float) deltaPos.length());
            position = position.add(deltaPos.multiply(speed * delta));
        }
    }

    public void die() {
        //TODO: delete the enemy
    }
}
