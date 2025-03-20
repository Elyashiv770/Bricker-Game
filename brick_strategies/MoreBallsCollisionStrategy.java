package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A collision strategy that creates a "puke" effect at the collision
 * location. The "puke" effect is represented by two instances created
 * at the center of the object that caused the collision.
 * @author Elyashiv Eitan
 */
public class MoreBallsCollisionStrategy implements CollisionStrategy
{
    private BrickerGameManager brickerGameManager;

    /**
     * Constructor for PukeCollisionStrategy. Initializes the strategy with
     * access to the game manager for creating "puke" effects.
     *
     * @param brickerGameManager The game manager instance responsible for
     *                           managing game objects and game logic.
     */
    public MoreBallsCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles a collision event by creating two "puke" effects at the
     * center of the object that caused the collision.
     *
     * @param thisObj  The game object that this strategy is associated with
     *                 (usually the brick).
     * @param otherObj The game object involved in the collision (e.g., the
     *                 ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj)
    {
        brickerGameManager.createPuck(otherObj.getCenter());
        brickerGameManager.createPuck(otherObj.getCenter());
    }
}
