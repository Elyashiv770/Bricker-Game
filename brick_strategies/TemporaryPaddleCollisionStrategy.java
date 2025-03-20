package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A collision strategy that creates a temporary paddle when a collision occurs.
 * The temporary paddle is created only if the counter is at zero.
 * @author Elyashiv Eitan
 */
public class TemporaryPaddleCollisionStrategy implements CollisionStrategy
{
    private BrickerGameManager brickerGameManager;

    /**
     * Constructor for TemporaryPaddleCollisionStrategy. Initializes the strategy
     * with access to the game manager.
     *
     * @param brickerGameManager The game manager instance responsible for
     *                           managing game objects and logic.
     */
    public TemporaryPaddleCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision event by creating a temporary paddle. A paddle is
     * only created if the temporary paddle counter value is zero.
     *
     * @param thisObj  The game object this strategy is associated with (usually
     *                 the brick).
     * @param otherObj The game object involved in the collision (e.g., the ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj)
    {
        if (this.brickerGameManager.temporaryPaddleCounter.value() == 0)
        {
            this.brickerGameManager.createTemporaryPaddle();

        }
    }
}
