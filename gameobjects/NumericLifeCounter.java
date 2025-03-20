package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Displays the number of lives (hearts) remaining in the game.
 * @author Elyashiv Eitan
 */
public class NumericLifeCounter extends GameObject
{
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private TextRenderable textRenderable;

    /**
     * Initializes a NumericLifeCounter object to display the heart count.
     *
     * @param position The top-left corner position of the counter.
     * @param size The size of the counter.
     * @param textRenderable The text renderable for displaying the count.
     * @param heartCount Initial count of hearts.
     */
    public NumericLifeCounter(Vector2 position,Vector2 size, TextRenderable
                                   textRenderable,int heartCount)
    {
        super(position, size, textRenderable);
        this.textRenderable = textRenderable;

        updateText(heartCount);
    }

    /**
     * Updates the color of the heart counter based on the heart count.
     *
     * @param heartCounter The current heart count.
     */
    public void updateColor(int heartCounter)
    {
        if (heartCounter == TWO)
        {
            this.textRenderable.setColor(Color.YELLOW);
        }
        else if (heartCounter == ONE)
        {
            this.textRenderable.setColor(Color.RED);
        }
        else if (heartCounter == THREE || heartCounter == FOUR)
        {
            this.textRenderable.setColor(Color.GREEN);
        }
    }

    /**
     * Updates the text to display the current heart count.
     * @param heartCounter The current heart count.
     */
    public void updateText(int heartCounter)
    {
        this.textRenderable.setString(String.valueOf(heartCounter));
    }
}
