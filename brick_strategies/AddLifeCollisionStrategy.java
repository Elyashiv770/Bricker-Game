package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A collision strategy that adds a life to the game by creating a fallen heart
 * at the collision point. This is triggered when a collision occurs between
 * two game objects.
 * @author Elyashiv Eitan
 */
public class AddLifeCollisionStrategy implements CollisionStrategy
{
    private BrickerGameManager brickerGameManager;

    /**
     * Constructor for AddLifeCollisionStrategy. Initializes the strategy
     * with a reference to the game manager.
     *
     * @param brickerGameManager The game manager instance that handles game
     *                           logic and state.
     */
    public AddLifeCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Called when a collision occurs between two game objects.
     *
     * @param thisObj  The initiating {@link GameObject}.
     * @param otherObj The {@link GameObject} being collided with.
     */
    @Override
    public void  onCollision(GameObject thisObj, GameObject otherObj)
    {
        brickerGameManager.createFallenHeart(otherObj.getCenter());
    }
}
