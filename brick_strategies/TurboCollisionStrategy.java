package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A strategy that handles collisions to enable turbo mode for the ball.
 * When a collision occurs with a game object (usually a brick), the ball's
 * speed is increased,and its appearance changes to indicate turbo mode.
 * @author Elyashiv Eitan
 */
public class TurboCollisionStrategy implements CollisionStrategy
{
    private final float MULT_SPEED = 1.4f;
    private BrickerGameManager brickerGameManager;

    /**
     * Constructs a TurboCollisionStrategy.
     *
     * @param brickerGameManager The BrickerGameManager instance controlling the game.
     */
    public TurboCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;

    }

    /**
     * Handles the collision event. If the ball is not already in turbo mode,
     * this method increases the ball's speed and changes its appearance to
     * indicate turbo mode.
     *
     * @param thisObj The game object involved in the collision (usually a brick).
     * @param otherObj The other game object involved in the collision (usually the ball).
     */
    @Override
    public void  onCollision(GameObject thisObj, GameObject otherObj)
    {
        if (!this.brickerGameManager.turboMode())
        {
            this.brickerGameManager.setTurbo(true);
            this.brickerGameManager.setTurboBall(MULT_SPEED);
        }

    }
}
