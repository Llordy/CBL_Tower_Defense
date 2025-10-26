package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;

/**class describing all turrets. */
public class Turret extends HealthEntity {

    int cost;
    Attack[] attacks;
    HashSet<HealthEntity> targets;
    boolean canDrawAttack = false;
    Vector drawTargetPos;

    /**constructor. */
    Turret(
        Attack[] attacks,
        int health,
        int cost,
        int width,
        int height,
        HashSet<HealthEntity> targets,
        String imagePathName
    ) {
        this.health = health;
        this.maxHealth = health;
        this.attacks = attacks;
        this.cost = cost;
        this.targets = targets;

        try {
            setBufferedImage(imagePathName, width, height);
        } catch (Exception e) {
            System.out.println("couldnt render image error TURRET");
            System.out.println(imagePathName);
            e.printStackTrace();
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
    }


    /**tries to attack with all its attacks. */
    private void attack() {
        
        HealthEntity target;
        
        for (Attack attack : attacks) {

            target = getTarget(attack, targets);

            if (target == null) {
                continue;
            }

            if (attack.range < this.distanceTo(target)) {
                continue;
            }

            if (attack.perform(this, target)) {
                canDrawAttack = true;
                drawTargetPos = target.position;
            }
        }
    }

    /**get a copy of this turret. */
    public Turret getCopy() {
        Attack[] newAttacks = new Attack[attacks.length];
        for (int i = 0; i < attacks.length; i++) {
            newAttacks[i] = attacks[i].getCopy();
        }
        
        Turret newTurret = new Turret(
            newAttacks,
            health, 
            cost, 
            width, 
            height, 
            targets, 
            imagePathName
            );
        return newTurret;
    }

    /**updates every frame. */
    public void update(double delta, HashSet<HealthEntity> targets) {
        this.targets = targets;

        if (targets != null) {
            attack();
        }
    }

    @Override
    void die() {
        
        for (DeathListener listener : deathListeners) {
            listener.entityDied("Turret", this);
        }
    }

    @Override
    void addDeathListener(DeathListener toAdd) {
        deathListeners.add(toAdd);
    }

    @Override
    void draw(Graphics2D g2) {
        super.draw(g2);

        if (canDrawAttack) {
            g2.setColor(Color.ORANGE);
            g2.drawLine(
                (int) position.x, 
                (int) position.y, 
                (int) drawTargetPos.x, 
                (int) drawTargetPos.y);
            canDrawAttack = false;
        }
    }
}