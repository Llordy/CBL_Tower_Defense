package main;

import java.util.HashSet;

/**interface for handling health and damage. */
public abstract class HealthEntity extends Entity {
    
    int maxHealth;
    int health;

    public HashSet<DeathListener> deathListeners = new HashSet<DeathListener>();

    /**takes damage. */
    public void damage(int amount) {

        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }

        if (this.health == 0) {
            System.out.print(this + " is dead");
            this.die();
        }
    }

    /**heal amount. */
    public void heal(int amount) {
        this.health += amount;

        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    abstract void die();

    abstract void addDeathListener(DeathListener toAdd);
}
