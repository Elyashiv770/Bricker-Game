package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * A collision strategy that applies two separate collision behaviors
 * for a single collision event. Each behavior is selected randomly
 * from the CollisionStrategyFactory.
 * @author Elyashiv Eitan
 */
public class DoubleBehvaviorCollisionStrategy implements CollisionStrategy
{
    private CollsionStrategyFactory collsionStrategyFactory;

    /**
     * Constructor for DoubleBehaviorCollisionStrategy.
     * Initializes the factory for generating random collision strategies.
     *
     * @param brickerGameManager The game manager instance responsible for
     * managing game-level objects and logic.
     */
    public DoubleBehvaviorCollisionStrategy(BrickerGameManager brickerGameManager)
    {
        this.collsionStrategyFactory = new CollsionStrategyFactory(brickerGameManager);
    }

    /**
     * Handles a collision event by applying two random collision behaviors.
     * The behaviors are fetched from the CollisionStrategyFactory and applied
     * sequentially.
     * @param thisObj  The game object involved in the collision (usually the brick).
     * @param otherObj The other game object involved in the collision (e.g., the ball).
     */
    @Override
    public void  onCollision(GameObject thisObj, GameObject otherObj)
    {

        this.collsionStrategyFactory.buildCollisionStrategy().onCollision(thisObj, otherObj);
        this.collsionStrategyFactory.buildCollisionStrategy().onCollision(thisObj, otherObj);

    }
}
