package main;

/**default grunt. */
public class EnemyGrunt extends Enemy {

    /**constructor. */
    EnemyGrunt() {
        super("/main/Images/enemy.png",
            30,
            30,
            20.0d,
            50,
            new Attack(10, 10, 1000)
        );
    }

}
