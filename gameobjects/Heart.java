package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a heart object in the game that adds health when collected.
 * @author Elyashiv EItan
 */
public class Heart extends GameObject
{
    BrickerGameManager brickerGameManager;

    /**
     * Initializes a Heart object.
     *
     * @param position The position of the heart.
     * @param dimensions The size of the heart.
     * @param renderable The renderable object for visual representation.
     * @param brickerGameManager Reference to the game manager.
     */
    public Heart(Vector2 position, Vector2 dimensions, Renderable renderable,
                                         BrickerGameManager brickerGameManager )
    {
        super(position,dimensions,renderable);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles collision with the paddle, adds health and removes the heart.
     *
     * @param other The other object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision)
    {
        if (shouldCollideWith(other))
        {
            this.brickerGameManager.addHeart();
            this.brickerGameManager.removeFallenHeart(this);
            this.brickerGameManager.incText();
        }
    }

    /**
     * Determines if this object should collide with another.
     *
     * @param other The other object to check collision with.
     * @return True if collision is valid, otherwise false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other)
    {
        return other.getTag().equals("main paddle");
    }
}
