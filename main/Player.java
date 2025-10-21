package main;

/** the player, should only be 1 of. */
public class Player extends HealthEntity {

    int money = 10;
    double speed;
    KeyHandler keyHandler;
    Hand hand = new Hand();
    GamePanel gamePanel;
    int armoryY;
    boolean canHandleTurret = true;
    int turretHandleDelayMillis = 100;

    

    /**describes all the actions the player does. */
    public class Hand {

        boolean handsFull = false;
        private Turret currentHeldTurret;

        /**places currently held turret at position. */
        public void placeTurret(Vector position) {
            handsFull = false;
            currentHeldTurret.position = new Vector(position);
            
            currentHeldTurret.addDeathListener(gamePanel);
            gamePanel.turrets.add(currentHeldTurret);
            
            currentHeldTurret = null;
        }

        /**sets held turret. */
        public void grabTurret(Turret turret) {
            handsFull = true;
            currentHeldTurret = turret;
        }

        /**returns currently held turret. */
        public Turret getTurret() {
            if (handsFull) {
                return currentHeldTurret;
            } else {
                return null;
            }
        }
    }

    /**constructor. */
    public Player(
        Vector position,
        float speed,
        int width,
        int height,
        int health,
        GamePanel gamePanel
    ) {
        this.gamePanel = gamePanel;
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

        updatePosition(delta);

        if (keyHandler.ePressed) {

            if (position.y < armoryY) { //OUTSIDE ARMORY
                
                if (hand.handsFull) {
                    hand.placeTurret(this.position);

                }

            } else { //INSIDE ARMORY
                
                gamePanel.armory.buyTurret(position.x);
            }
        }
    }

    private void updatePosition(double delta) {

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

    /**you die, skill issue.
     * will call gamepanel, which in turn handles the deathscreen and such.
    */
    @Override
    void die() {
        for (DeathListener listener : deathListeners) {
            listener.entityDied("Player", this);
        }
    }

    /**
     * adds a listener to be called up death.
     */
    @Override
    void addDeathListener(DeathListener toAdd) {
        deathListeners.add(toAdd);
    }
}
