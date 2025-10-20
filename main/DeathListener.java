package main;

/**event for when an entity dies. */
public interface DeathListener {
    void entityDied(String classID, Entity deadEntity);
}
