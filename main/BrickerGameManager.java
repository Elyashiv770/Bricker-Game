package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.CollsionStrategyFactory;
import bricker.brick_strategies.TemporaryPaddleCollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.awt.event.KeyEvent;

/**
 * BrickerGameManager is the main class responsible for managing the Bricker game.
 * It initializes, updates, and manages all game components such as the ball, paddle,
 * bricks, lives, and other gameplay mechanics.
 *
 * This class handles the following responsibilities:
 *  - Setting up the game environment.
 *  - Managing user input and game updates.
 *  - Handling collisions, game over conditions, and game reset functionality.
 * @author Elyashiv Eitan
 */
public class BrickerGameManager extends GameManager
{
    // Final definition
    private static final int BORDER_WIDTH = 5;
    private static final int BORDER_OUTSIDE_FRAME = 200;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BRICK_HEIGHT = 15;
    private static final int PADDLE_GAP_FROM_BOTTOM = 30;
    private static final int GAP_BET_BRICKS = 2;
    private static final int GAP_BET_LIFE = 5;
    private static final int BRICK_COLS = 8;
    private static final int BRICK_ROWS = 7;
    private static final int LIFE_GAP_FROM_BRODER = 40;
    private static final int LIFE = 5;
    private static final int MAX_LIFE = 5;
    private static final int HEART_WIDTH = 20;
    private static final int HEART_HEIGHT = 20;
    private static final int BALL_RADIUS = 20;
    private static final int UI_LAYER = 200;
    private static final int SPEED_OF_HEART = 100;
    private static final float BALL_SPEED = 200;
    private static final float PUKE_RADIUS_RATIO = 0.75F;
    private static final float MULT_SPEED = 1.4f;

    // Final String definition
    private static final String BALL_IMAGE_PATH = "assets/ball.png";
    private static final String TURBO_BALL_IMAGE_PATH = "assets/redball.png";
    private static final String PUKE_IMAGE_PATH = "assets/mockBall.png";
    private static final String BALL_SOUND_PATH = "assets/blop.wav";
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final String BACKROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final String GAME_TITLE = "brick";


    // Variable definition
    private int heartCounter = LIFE;
    private int brickRows;
    private int brickCols;
    private int brickCounter;

    // Object definition
    private Ball ball;
    private NumericLifeCounter numericLifeCounter;
    private GameObject[] hearts;
    private List<GameObject> pukes = new ArrayList<>();
    private List<GameObject> fallenHeart = new ArrayList<>();
    private Vector2 windowDimensions;
    private WindowController windowController;
    private TextRenderable textRenderable;
    private UserInputListener userInputListener;
    private Renderable brickRender;
    private Renderable heartRender;
    private Renderable ballRender;
    private Renderable redBallRender;
    private Renderable pukeBallRender;
    private Renderable paddleRender;
    private Renderable backgroundRender;
    private Sound ballSound;
    private Boolean turboState = false;
    public Counter temporaryPaddleCounter = new Counter(0);
    public Counter doubleStrategrCounter = new Counter(0);

    /**
     * Main method to initialize and run the Bricker game.
     * Creates a BrickerGameManager instance with a title and window dimensions,
     * then starts the game.
     */
    public static void main(String[] args)
    {

        int brickRows;
        int brickCols;
        if (args.length == 2)
        {
            brickRows = Integer.parseInt(args[0]);
            brickCols = Integer.parseInt(args[1]);
        }
        else
        {
            brickRows = BRICK_ROWS;
            brickCols = BRICK_COLS;
        }
        BrickerGameManager brickerGameManager= new
                BrickerGameManager(GAME_TITLE,new Vector2(WINDOW_WIDTH,WINDOW_HEIGHT)
                                                          ,brickRows,brickCols);
        brickerGameManager.run();
    }

    /**
     * Initializes a new BrickerGameManager instance with the specified window
     * title and dimensions.
     *
     * @param windowTitle the title of the game window.
     * @param windowDimensions the dimensions of the game window as a Vector2.
     */
    BrickerGameManager(String windowTitle,Vector2 windowDimensions,int brickRows,
                                                                   int brickCols)
    {
        super(windowTitle,windowDimensions);
        this.brickRows = brickRows;
        this.brickCols = brickCols;
        this.brickCounter = this.brickCols*this.brickRows;
    }

    /**
     * Initializes the Bricker game by setting up all necessary game components,
     * including the ball, paddle, borders, background, bricks, lives, and score.
     *
     * @param imageReader      a utility for reading images.
     * @param soundReader      a utility for reading and playing sounds.
     * @param inputListener    listens for user input events.
     * @param windowController controls the game window's properties and behavior.
     */
    @Override
    public void initializeGame(ImageReader imageReader,SoundReader soundReader,
                               UserInputListener inputListener,WindowController
                                       windowController)
    {
        super.initializeGame(imageReader, soundReader, inputListener,
                windowController);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.userInputListener = inputListener;
        this.heartRender = imageReader.readImage(HEART_IMAGE_PATH,true);
        this.ballRender = imageReader.readImage(BALL_IMAGE_PATH,true);
        this.pukeBallRender = imageReader.readImage(PUKE_IMAGE_PATH,true);
        this.paddleRender = imageReader.readImage(PADDLE_IMAGE_PATH,false);
        this.backgroundRender = imageReader.readImage(BACKROUND_IMAGE_PATH,false);
        this.ballSound = soundReader.readSound(BALL_SOUND_PATH);
        this.brickRender = imageReader.readImage(BRICK_IMAGE_PATH,false);
        this.redBallRender = imageReader.readImage(TURBO_BALL_IMAGE_PATH,true);

        // Creat the beginning of the game
        createBall();
        createBricks();
        createHearts();
        createNumeric();
        createPaddle();
        createBorders();
        createBackroud();
    }

    /**
     * Removes a brick from the game world and decreases the brick counter.
     *
     * @param brick The Brick object to be removed from the game.
     *              Must be part of the game objects collection.
     */
    public void removeBrick(Brick brick)
    {
        if (gameObjects().removeGameObject(brick))
        {
            this.brickCounter--;
        }
    }

    /**
     * Checks if the ball is in turbo mode.
     * @return True if the ball is in turbo mode, false otherwise.
     */
    public boolean turboMode()
    {
        return turboState;
    }

    /**
     * Toggles the object's turbo mode, affecting its behavior or properties.
     *
     * @param turbo True to enable turbo mode, false to disable it.
     */
    public void setTurbo(boolean turbo)
    {
        this.turboState = turbo;
    }

    /**
     * Removes a temporary paddle from the game and updates the paddle counter.
     * @param temporaryPaddle The TemporaryPaddle object to be removed.
     */
    public void removeTemporaryPaddle(TemporaryPaddle temporaryPaddle)
    {
        gameObjects().removeGameObject(temporaryPaddle);
        this.temporaryPaddleCounter.reset();

    }

    /**
     * Resets the ball to its original state by restoring its velocity and appearance.
     * Disables turbo mode.
     */
    public void setBallToOriginal()
    {
        this.ball.setVelocity(new Vector2(BALL_SPEED, BALL_SPEED));
        this.ball.renderer().setRenderable(this.ballRender);
        this.turboState = false;
    }

    /**
     * Sets the ball to turbo mode by increasing its velocity and changing its
     * @param multSpeed The multiplier to apply to the ball's current speed.
     */
    public void setTurboBall(float multSpeed)
    {
        this.ball.setVelocity(new Vector2(this.ball.getVelocity().x() *
                multSpeed,this.ball.getVelocity().y() * multSpeed));

        this.ball.renderer().setRenderable(this.redBallRender);
        this.turboState = true;
    }

    /**
     * Creates a falling heart at the specified position with a downward velocity.
     *
     * @param position Starting position of the heart. Must not be null.
     */
    public void createFallenHeart(Vector2 position)
    {
        GameObject fallenheart = new Heart(position,new
                Vector2(HEART_WIDTH, HEART_HEIGHT),heartRender,this);
        gameObjects().addGameObject(fallenheart);
        fallenheart.setVelocity(new Vector2(0, SPEED_OF_HEART));
    }

    /**
     * Creates a new puck ball at the center of the screen, assigns a random
     * velocity,  and adds it to the game objects.
     */
    public void createPuck(Vector2 position)
    {
        // Create a new puck ball with specified dimensions, image, and sound.
        GameObject puckBall = new Ball(
                position, new Vector2(BALL_RADIUS * PUKE_RADIUS_RATIO,
                BALL_RADIUS * PUKE_RADIUS_RATIO)  , this.pukeBallRender,
                this.ballSound,this);
        gameObjects().addGameObject(puckBall);
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float ballVelX = (float)Math.cos(angle) *  BALL_SPEED;
        float ballVelY = (float)Math.sin(angle) *  BALL_SPEED;
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        puckBall.setVelocity(new Vector2(ballVelX, ballVelY));
        this.pukes.add(puckBall);
    }

    /**
     * Creates and positions a temporary paddle at the center of the window.
     * Increments the temporary paddle counter and adds it to the game objects.
     */
    public void createTemporaryPaddle()
    {
        GameObject tempPaddle = new TemporaryPaddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                this.paddleRender,this.userInputListener,
                this.windowDimensions,this);

        tempPaddle.setCenter( new Vector2(windowDimensions.x()/2,
                windowDimensions.y()/2));
        this.temporaryPaddleCounter.increaseBy(1);
        gameObjects().addGameObject(tempPaddle);
        this.temporaryPaddleCounter.increment();
    }

    /**
     * Removes the specified heart object from the game.
     * @param heart The heart GameObject to be removed.
     */
    public void removeFallenHeart(GameObject heart)
    {
        gameObjects().removeGameObject(heart);
    }

    /**
     * Adds a heart to the game, updating the life counter and rendering
     * it on the screen, provided the maximum life count is not exceeded.
     */
    public void addHeart()
    {
        if (heartCounter < MAX_LIFE)
        {
            float y = this.windowDimensions.y() - HEART_HEIGHT;
            float x =  (this.heartCounter*(HEART_WIDTH + GAP_BET_LIFE) +
                    LIFE_GAP_FROM_BRODER);
            GameObject heart = new Heart(new Vector2(x,y),new
                    Vector2(HEART_WIDTH, HEART_HEIGHT),this.heartRender,this);
            gameObjects().addGameObject(heart,Layer.UI);
            this.hearts[this.heartCounter] = heart;
            this.heartCounter++;
            this.fallenHeart.add(heart);
        }
    }

    /**
     * Updates the text and color of the numeric life counter display based on
     * the current heart count.
     * This method modifies the display by:
     * 1. Updating the displayed text to match the value of heartCounter.
     * 2. Changing the text color depending on the value of heartCounter.
     */
    public void incText()
    {
        this.numericLifeCounter.updateText(this.heartCounter);
        this.numericLifeCounter.updateColor(this.heartCounter);

    }


    /**
     * Creates a grid of bricks, positions them on the screen
     */
    private void createBricks()

    {
        int brickWidth = ((int)this.windowDimensions.x() -
                (GAP_BET_BRICKS* brickCols)- 1 - (2*BORDER_WIDTH))/brickCols ;
        CollsionStrategyFactory collsionStrategyFactory = new
                CollsionStrategyFactory(this);
        for (int i = 0; i < brickRows; i++)
        {
            for(int j = 0; j < brickCols; j++)
            {
                float x = j * (brickWidth + GAP_BET_BRICKS ) + GAP_BET_BRICKS +
                        BORDER_WIDTH ;
                float y = i * (BRICK_HEIGHT + GAP_BET_BRICKS) + GAP_BET_BRICKS +
                        BORDER_WIDTH ;

              CollisionStrategy randStrategy = collsionStrategyFactory.buildCollisionStrategy();
              this.doubleStrategrCounter.reset();
                GameObject brick = new Brick( new Vector2(x, y), new
                        Vector2(brickWidth, BRICK_HEIGHT),this.brickRender,
                        this.gameObjects(),this,randStrategy);
                gameObjects().addGameObject(brick);
            }
        }
    }

    /**
     * Creates and places the user's paddle at the bottom of the screen.
     */
    private void createPaddle()

    {
        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                this.paddleRender,
                this.userInputListener,this.windowDimensions);
        userPaddle.setCenter(
                new Vector2(this.windowDimensions.x()/2,
                        (int)this.windowDimensions.y()-PADDLE_GAP_FROM_BOTTOM));
        gameObjects().addGameObject(userPaddle);
        userPaddle.setTag("main paddle");
    }

    /**
     * Updates the game state, including cleaning up off-screen objects, handling
     * ball fall, and checking if the game has ended.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        // Delete off-screen pukes and fallen hearts
        cleanUpFallenObjects(pukes);
        cleanUpFallenObjects(fallenHeart);

        // Handle the case where the ball falls below the window
        handleBallFall();
        checkForGameEnd();
    }


    /**
     * Creates a ball, sets its position and velocity, and adds it to the game.
     */
    private void createBall()
    {
        this.ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS),
                this.ballRender, this.ballSound,this);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }


    /**
     * Creates and adds borders to the left and right sides of the game window.
     *
     */
    private void createBorders() {
        // Left border (outside the left edge)
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(-BORDER_OUTSIDE_FRAME, 0),
                        new Vector2(BORDER_OUTSIDE_FRAME, this.windowDimensions.y()),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(this.windowDimensions.x(), 0),
                        new Vector2(BORDER_OUTSIDE_FRAME, this.windowDimensions.y()),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(0, -BORDER_OUTSIDE_FRAME),
                        new Vector2(this.windowDimensions.x(), BORDER_OUTSIDE_FRAME),
                        null)
        );
    }



    /**
     * Creates a background covering the entire game window and adds it to the game.
     *
     */
    private void createBackroud()
    {
        GameObject backRound = new GameObject(Vector2.ZERO,this.windowDimensions,
                this.backgroundRender);
        gameObjects().addGameObject(backRound, Layer.BACKGROUND);
    }

    /**
     * Creates and positions heart objects to represent lives in the game.
     * Hearts are placed along the bottom of the window and added to the UI layer.
     */
    private void createHearts()
    {
        this.hearts = new GameObject[MAX_LIFE];
        float x = LIFE_GAP_FROM_BRODER;
        float y = this.windowDimensions.y() - HEART_HEIGHT;
        for (int i = 0; i < LIFE; i++)
        {
            x =  (i*(HEART_WIDTH + GAP_BET_LIFE) + LIFE_GAP_FROM_BRODER);
            GameObject life = new Heart(new Vector2(x,y),new
                    Vector2(HEART_WIDTH, HEART_HEIGHT),this.heartRender,this);
            gameObjects().addGameObject(life,Layer.UI);
            this.hearts[i] = life;
        }
    }

    /**
     * Creates a numeric display showing the current life count in the game.
     */
    private void createNumeric()
    {
        // Create a text renderable for displaying the life count
        this.textRenderable = new TextRenderable(String.valueOf(this.heartCounter));
        textRenderable.setColor(Color.GREEN); // Set the text color

        // Calculate the position for the numeric life counter
        Vector2 size = new Vector2(BORDER_WIDTH + GAP_BET_LIFE,
                this.windowDimensions.y() - HEART_HEIGHT);

        // Create a new NumericLifeCounter object
        this.numericLifeCounter = new NumericLifeCounter(
                size,new Vector2(HEART_WIDTH, HEART_HEIGHT), textRenderable,
                                                             this.heartCounter);

        // Add the numeric object to the game
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }



    /**
     * Cleans up objects that have fallen outside the window.
     * @param objects List of game objects to check for being out of bounds.
     */
    private void cleanUpFallenObjects(List<GameObject> objects)
    {
        for (GameObject gameObject : objects)
        {
            if (gameObject.getCenter().y() > windowDimensions.y())
            {
                this.gameObjects().removeGameObject(gameObject);
            }
        }
    }

    /**
     * Handles the situation when the ball falls below the window.
     * Reduces the life count and updates the UI accordingly.
     */
    private void handleBallFall()
    {
        if (this.ball.getCenter().y() > windowDimensions.y())
        {
            this.heartCounter--;
            this.gameObjects().removeGameObject(this.hearts[heartCounter], UI_LAYER);
            this.numericLifeCounter.updateColor(heartCounter);
            this.numericLifeCounter.updateText(heartCounter);
            ball.setCenter(windowDimensions.mult(0.5f));

            float ballVelX;
            float ballVelY;

            if (this.turboState)
            {
                ballVelX = BALL_SPEED * MULT_SPEED;
                ballVelY = BALL_SPEED * MULT_SPEED;
            }
            else
            {
                ballVelX = BALL_SPEED;
                ballVelY = BALL_SPEED;
            }

            Random rand = new Random();
            if(rand.nextBoolean())
                ballVelX *= -1;
            if(rand.nextBoolean())
                ballVelY *= -1;
            ball.setVelocity(new Vector2(ballVelX, ballVelY));
            // Update the UI counter
        }
    }

    /**
     * Checks if the game should end based on the number of lives or bricks.
     */
    private void checkForGameEnd() {
        String prompt = "";

        if (this.heartCounter == 0)
        {
            prompt = "Game Over!";
        }

        else if (this.brickCounter == 0 || userInputListener.
                                                    isKeyPressed(KeyEvent.VK_W))
        {
            prompt = "You win!";
        }
        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt))
            {
                windowController.resetGame();
                this.turboState = false;
                this.heartCounter = LIFE;
                this.brickCounter = brickCols * brickRows;
                this.temporaryPaddleCounter.reset();
            } else {
                windowController.closeWindow();
            }
        }
    }
}

