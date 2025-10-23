package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**a generic attack. */
public class Attack {

    int damage;
    double range;
    private int fireDelay; //millis

    boolean onCooldown = false;

    /**constructor, firerate is attacks per second. */
    Attack(int damage, int range, int fireDelay) {

        this.damage = damage;
        this.range = range;
        this.fireDelay = fireDelay;
    }

    /**performs the attack on the given target. */
    public void perform(Vector origin, HealthEntity target) {

        if (target == null) {
            return;
        }

        if (!onCooldown) {
            
            target.damage(damage);
            onCooldown = true;

            animate(origin, target);

            //cooldown timer
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onCooldown = false;
                }
            };
            Timer timer = new Timer(fireDelay, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void animate(Vector origin, HealthEntity target) {
        //TODO: add attack animation
    }
}
