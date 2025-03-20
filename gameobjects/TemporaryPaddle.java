package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a temporary paddle that is removed after a certain number of col
 * @author Elyashiv Eitan
 */
public class TemporaryPaddle extends Paddle
{
    private final int PADDLE_MAX_COLLISION = 4;
    private int paddleCounter = PADDLE_MAX_COLLISION;
    private BrickerGameManager brickerGameManager;

    /**
     * Initializes a TemporaryPaddle object.
     *
     * @param topLeftCorner The top-left corner position of the paddle.
     * @param dimensions The dimensions of the paddle.
     * @param renderable The renderable object for visual representation.
     * @param inputListener The listener for user input.
     * @param windowDimensions The window dimensions.
     * @param brickerGameManager Reference to the game manager.
     */
    public TemporaryPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                           Renderable renderable, UserInputListener
                                   inputListener, Vector2 windowDimensions,
                                          BrickerGameManager brickerGameManager)
    {
        super(topLeftCorner, dimensions, renderable, inputListener,
                                                              windowDimensions);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles collision with other game objects. Decreases the paddle counter
     * and removes the paddle when it reaches 0.
     * @param other The other object involved in the collision.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision)
    {
        super.onCollisionEnter(other, collision);

        // Delete the object
        this.paddleCounter--;
        if (this.paddleCounter == 0)
        {
            this.brickerGameManager.removeTemporaryPaddle(this);
            this.paddleCounter = PADDLE_MAX_COLLISION;
        }
    }
}




