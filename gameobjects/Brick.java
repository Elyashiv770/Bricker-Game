package bricker.gameobjects;

import bricker.brick_strategies.*;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.collisions.Collision;

/**
 * Represents a brick in the Bricker game. A brick is a game object that can
 * collide with other objects and be removed upon collision.
 * @author Elyashiv Eitan
 */
public class Brick extends GameObject
{
    private BrickerGameManager brickerGameManager;
    private CollisionStrategy collisionStrategy;
    private GameObjectCollection collisions;

    /**
     * Constructs a new Brick instance.
     *
     * @param topLeftCorner the position of the top-left corner of the brick.
     * @param dimensions    the dimensions (width and height) of the brick.
     * @param renderable    the visual representation of the brick.
     * @param collisions    the collection of game objects, used to remove the
     *                      brick upon collision.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection collisions, BrickerGameManager
                         brickerGameManager,CollisionStrategy collisionStrategy)
    {
        super(topLeftCorner, dimensions, renderable);
        this.collisions = collisions;
        this.brickerGameManager = brickerGameManager;
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Called when this brick collides with another game object.
     * The brick is removed from the game upon collision.
     *
     * @param other     the game object this brick collided with.
     * @param collision information about the collision event.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision)
    {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this,other);
        this.brickerGameManager.removeBrick(this);
    }
}
