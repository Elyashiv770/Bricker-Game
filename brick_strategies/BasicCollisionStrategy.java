package bricker.brick_strategies;

import danogl.GameObject;

/**
 * A basic collision strategy that handles the collision event between game
 * objects. Currently, it doesn't implement any logic, but can be extended
 * to provide specific behavior when collisions occur.
 * @author Elyashiv Eitan
 */
public  class BasicCollisionStrategy implements CollisionStrategy
{
    /**
     * Default constructor for BasicCollisionStrategy.
     * Initializes the strategy with no additional state or logic.
     */
    public BasicCollisionStrategy()
    {
    }

    /**
     * Handles a collision event between two game objects. This is a placeholder
     * implementation and does not perform any actions on collision.
     *
     * @param thisObj  The game object associated with this strategy (usually
     *                 a brick).
     * @param otherObj The other game object involved in the collision (e.g.,
     *                 the ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj)
    {
    }
}
