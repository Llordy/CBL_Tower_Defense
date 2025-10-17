package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**a generic attack. */
public class Attack {

    int damage;
    double range;
    int firerate;
    private int fireDelay; //millis

    boolean onCooldown = false;

    /**constructor, firerate is attacks per second. */
    Attack(int damage, int range, int firerate) {

        this.damage = damage;
        this.range = range;
        this.firerate = firerate;
        this.fireDelay = Math.round((float) 1000.0d / firerate);
    }

    /**performs the attack on the given target. */
    public void perform(HealthEntity target) {

        if (!onCooldown) {
            
            target.damage(damage);
            onCooldown = true;

            //TODO: animate

            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onCooldown = false;
                }
            };
            new Timer(fireDelay, taskPerformer).start();
        }
    }
}
