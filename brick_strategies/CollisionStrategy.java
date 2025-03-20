package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface for defining collision handling behavior between game objects.
 * @author Elyashiv Eitan
 */
public interface CollisionStrategy
{
    /**
     * Called when a collision occurs between two game objects.
     *
     * @param thisObj  The initiating {@link GameObject}.
     * @param otherObj The {@link GameObject} being collided with.
     */
    public void  onCollision(GameObject thisObj, GameObject otherObj);
}
