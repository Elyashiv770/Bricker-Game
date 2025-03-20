package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle in the game that responds to user input for movement.
 * The paddle moves horizontally within the game window and is restricted
 * from moving outside the window bounds.
 * @author Elyashiv Eitan
 */
public class Paddle extends GameObject
{
    private static final float MOVEMENT_SPEED = 400;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates
     *  (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null,
     *  in which case
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                      Renderable renderable, UserInputListener inputListener,Vector2 windowDimensions)
    {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;

    }

    /**
     * Updates paddle position based on user input, ensuring it stays within window bounds.
     * Moves left if the left arrow key is pressed and paddle is not at the left edge.
     * Moves right if the right arrow key is pressed and paddle is not at the right edge.
     * Updates velocity based on direction and speed, using deltaTime for smooth movement.
     *
     * @param deltaTime Time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;

        // Check for left movement and ensure it stays within bounds
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            if (getTopLeftCorner().x() > 0)
            {
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }

        // Check for right movement and ensure it stays within bounds
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            if (getTopLeftCorner().x() + getDimensions().x() < windowDimensions.x())
            {
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        // Update velocity and position
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
