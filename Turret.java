package main;

import java.util.ArrayList;

/**class describing all turrets. */
public class Turret extends HealthEntity {

    int cost;
    Attack[] attacks;
    ArrayList<HealthEntity> targets;

    /**constructor. */
    Turret(Attack[] attacks, int cost, ArrayList<HealthEntity> targets) {

        this.attacks = attacks;
        this.cost = cost;
        this.targets = targets;
    }

    /**decides the best target for the given attack. */
    private HealthEntity getTarget(Attack attack, ArrayList<HealthEntity> possibleTargets) {
        
        HealthEntity maxTarget = targets.get(0);
        float maxHeat = getHeat(maxTarget);
        

        for (HealthEntity target : targets) {

            if (getHeat(target) > maxHeat) {

                maxTarget = target;
                maxHeat = getHeat(target);
            }
        }
        
        return maxTarget;
    }

    private float getHeat(HealthEntity target) {
        return 0.0f;
        //TODO: calculate the heat
    }


    /**tries to attack with all its attacks. */
    private void attack() {

        for (Attack attack : attacks) {

            attack.perform(getTarget(attack, targets));
            //TODO: fix targeting and gather targets
        }
    }

    public void update(double delta, ArrayList<Enemy> enemies) {
        attack();
    }

    @Override
    void die() {
        // TODO turret go kaboom
        throw new UnsupportedOperationException("Unimplemented method 'die'");
    }
}
