package main;

import java.util.HashSet;

/**default class for each enemy. */
public class Enemy extends HealthEntity {

    double speed;
    HealthEntity currentTarget;
    HashSet<HealthEntity> savedTargets;
    Attack attack;

    /**constructor. */
    Enemy(
        String pathName,
        int width,
        int height,
        double speed,
        int health,
        Attack attack
    ) {
        try {
            setBufferedImage(pathName, width, height);
        } catch (Exception e) {
            System.out.println("couldnt render image error ENEMY");
        }
        
        this.speed = speed;
        this.attack = attack;
        this.health = health;
    }

    /**returns the priority target.
     * currently distance based
    */
    private HealthEntity decideTarget(HashSet<HealthEntity> healthEntities) {
        
        double minDistance = 1000000d;
        HealthEntity minTarget = null;

        for (HealthEntity target : healthEntities) {

            if (this.distanceTo(target) < minDistance) {

                minDistance = this.distanceTo(target);
                minTarget = target;
            }
        }

        return minTarget;
    }

    /**updates every frame. */
    public void update(double delta, HashSet<Turret> turrets, Player player) {

        savedTargets = new HashSet<HealthEntity>(turrets);
        savedTargets.add(player);

        currentTarget = decideTarget(savedTargets);

        moveTowards(currentTarget, delta);

        

        if (this.distanceTo(currentTarget) <= attack.range) {

            attack.perform(position, currentTarget);
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

    /**get a copy of this enemy. */
    public Enemy getCopy() {
        Enemy newEnemy = new Enemy(imagePathName, width, height, speed, health, attack);
        return newEnemy;
    }
    
    /**enemy dies. */
    public void die() {
        for (DeathListener listener : deathListeners) {
            listener.entityDied("Enemy", this);
        }
    }

    @Override
    void addDeathListener(DeathListener toAdd) {
        deathListeners.add(toAdd);
    }
}
