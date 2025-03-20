package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import java.util.Random;

/**
 * Factory class responsible for creating various collision strategies based on
 * the current game state. The factory can generate strategies for basic,
 * temporary paddles, turbo modes, and more, with a special rule for
 * double behavior strategies.
 * @author Elyashiv Eitan
 */
public class CollsionStrategyFactory
{
    // Final definition
    private final int TWO = 2;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;
    private final int SEVEN = 7;
    private final int EIGHT = 8;
    private final int NINE = 9;
    private final int TEN = 10;
    private BrickerGameManager brickerGameManager;
    private Random rand;
    private int strategyRand;


    /**
     * Constructor for CollsionStrategyFactory.
     * Initializes the factory with a reference to the game manager and a random
     * number generator for strategy selection.
     *
     * @param brickerGameManager The game manager instance responsible for managing
     *                           the state of the game.
     */
    public CollsionStrategyFactory(BrickerGameManager brickerGameManager)
    {
        this.brickerGameManager = brickerGameManager;
        rand = new Random();
    }

    /**
     * Builds a new collision strategy based on the current game state and
     * the number of double behavior strategies that have been used.
     * If the double behavior counter is less than 2, a strategy is selected
     * from 9 options, otherwise it selects from 8 options.
     *
     * @return A CollisionStrategy instance based on the random selection.
     */
    public CollisionStrategy buildCollisionStrategy()
    {
        if(brickerGameManager.doubleStrategrCounter.value() < TWO)
        {
            strategyRand = rand.nextInt(TEN);
            this.brickerGameManager.doubleStrategrCounter.increment();
        }
        else
        {
            strategyRand = rand.nextInt(NINE);
        }
        CollisionStrategy collisionStrategy = null;

        if (strategyRand >= 0 && strategyRand <= FOUR)
        {
            collisionStrategy = new BasicCollisionStrategy();

        }
        else if(strategyRand == FIVE)
        {
            collisionStrategy = new MoreBallsCollisionStrategy(brickerGameManager);
        }
        else if (strategyRand == SIX)
        {
            collisionStrategy = new AddLifeCollisionStrategy(brickerGameManager);

        }
        else if (strategyRand == SEVEN)
        {
            collisionStrategy = new TemporaryPaddleCollisionStrategy(brickerGameManager);

        }
        else if (strategyRand == EIGHT)
        {
            collisionStrategy = new TurboCollisionStrategy(brickerGameManager);
        }
        else if (strategyRand == NINE)
        {
            collisionStrategy = new DoubleBehvaviorCollisionStrategy(brickerGameManager);
        }
        brickerGameManager.doubleStrategrCounter.increaseBy(1);
        return collisionStrategy;
    }
}
