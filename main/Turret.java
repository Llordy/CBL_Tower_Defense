package main;

import java.util.HashSet;

/**class describing all turrets. */
public class Turret extends HealthEntity {

    int cost;
    Attack[] attacks;
    HashSet<HealthEntity> targets;

    /**constructor. */
    Turret(
        Attack[] attacks,
        int health,
        int cost,
        int width,
        int height,
        HashSet<HealthEntity> targets
    ) {
        this.health = health;
        this.maxHealth = health;
        this.attacks = attacks;
        this.cost = cost;
        this.targets = targets;

        try {
            setBufferedImage("/main/Images/placeholderTurret.png", width, height);
        } catch (Exception e) {
            System.out.println("couldnt render image error TURRET");
        }
    }

    /**decides the best target for the given attack. */
    private HealthEntity getTarget(Attack attack, HashSet<HealthEntity> possibleTargets) {
        
        HealthEntity maxTarget = null;
        double maxHeat = 0d;

        for (HealthEntity target : targets) {

            if (getHeat(target) > maxHeat) {

                maxTarget = target;
                maxHeat = getHeat(target);
            }
        }
        
        return maxTarget;
    }

    private double getHeat(HealthEntity target) {
        return 100 / distanceTo(target);
        //TODO: calculate the heat
    }


    /**tries to attack with all its attacks. */
    private void attack() {
        
        HealthEntity target;
        
        for (Attack attack : attacks) {

            target = getTarget(attack, targets);

            if (attack.range < this.distanceTo(target)) {
                continue;
            }

            attack.perform(position, target);
            
            //TODO: fix targeting and gather targets
        }
    }

    /**get a copy of this turret. */
    public Turret getCopy() {
        Turret newTurret = new Turret(attacks, health, cost, width, height, targets);
        return newTurret;
    }

    /**updates every frame. */
    public void update(double delta, HashSet<HealthEntity> targets) {
        this.targets = targets;
        attack();
    }

    @Override
    void die() {
        // TODO turret go kaboom
        
        for (DeathListener listener : deathListeners) {
            listener.entityDied("Turret", this);
        }
    }

    @Override
    void addDeathListener(DeathListener toAdd) {
        deathListeners.add(toAdd);
    }
}