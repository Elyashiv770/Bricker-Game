package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a ball in the Bricker game. The ball can move, collide with other
 * objects, and bounce off surfaces. It also tracks the number of collisions
 * and plays a sound upon each collision.
 * @author Elyashiv Eitan
 */
public class Ball extends GameObject
{

    private final int MAX_TURBO_COLISION= 6;
    private int turboCounter = 0;
    private int collisionCounter = 0;
    private Sound collisionSound;
    private BrickerGameManager brickerGameManager;


    /**
     * Constructs a new Ball instance.
     *
     * @param topLeftCorner  the position of the ball's top-left corner in
     *                       window coordinates (pixels). (0,0) is the top-left.
     * @param dimensions     the width and height of the ball in window
     *                       coordinates.
     * @param renderable     the visual representation of the ball. Can be null.
     * @param collisionSound the sound to play on each collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, BrickerGameManager brickerGameManager)
    {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.collisionSound = collisionSound;
    }

    /**
     * Called when the ball collides with another game object. The ball bounces
     * off by flipping its velocity along the collision normal and increments
     * the collision counter. A sound is also played.
     *
     * @param other     the game object the ball collided with.
     * @param collision information about the collision, including the normal.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision)
    {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;
        handleTurboMode();
    }

    /**
     * Retrieves the current collision counter value.
     * @return The current value of the collision counter.
     */
    public int getCollisionCounter()
    {
        return this.collisionCounter;
    }

    /**
     * Handles the turbo mode functionality. If the ball is in turbo mode, the turbo
     * counter is incremented. Once the counter exceeds the maximum allowed turbo
     * collisions, the ball is reset to its original state and the counter is reset.
     */
    private void handleTurboMode()
    {
        if (brickerGameManager.turboMode())
        {
            this.turboCounter++;
            if (this.turboCounter >= MAX_TURBO_COLISION)
            {
                brickerGameManager.setBallToOriginal();
                this.turboCounter = 0;
            }
        }
    }

}
