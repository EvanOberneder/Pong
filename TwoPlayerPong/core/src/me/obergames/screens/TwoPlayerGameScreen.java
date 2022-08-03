package me.obergames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import me.obergames.game.TwoPlayerPong;

public class TwoPlayerGameScreen implements Screen {

	// Declare variables
	private TwoPlayerPong game;
	private Rectangle playerOnePaddle, playerTwoPaddle;
	private Circle pongBall;
	float circleVelX;
	float circleVelY;
	
	// Constants/variables
	private final int PIXEL_BUFFER = 5;
	private final int PADDLE_SPEED = 500;
	private final int PADDLE_HEIGHT = 125;
	private final int PADDLE_WIDTH = 20;
	private final int CIRCLE_RADIUS = 20;
	private boolean hasStarted = false; // Game will initially not be started
	private boolean initialStart;
	float circleX = TwoPlayerPong.GAME_WIDTH / 2;
	float circleY = TwoPlayerPong.GAME_HEIGHT / 2;
	
	// Set score to 0
	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	
	// OnePlayerGameScreen Constructor - parameter to pass Game instance
	public TwoPlayerGameScreen (TwoPlayerPong game) {
		
		this.game = game;
		
		// x , y , width, height
		playerOnePaddle = new Rectangle(50, TwoPlayerPong.GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		playerTwoPaddle = new Rectangle(TwoPlayerPong.GAME_WIDTH - 50 - PADDLE_WIDTH, TwoPlayerPong.GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
	
		// x, y, radius
		// Create circle object to use for our pong ball
		pongBall = new Circle(circleX, circleY, CIRCLE_RADIUS);
		
		// Switch sides the ball goes to every time.
		// Assign variables
		circleVelX = game.random.nextInt(100) + 300;
		circleVelY = 115;
		initialStart = true;
		
	}
	
	
	@Override
	public void show() {
		
		
	}

	
	// Render method called 60 times a second
	@Override
	public void render(float delta) {
		
		ScreenUtils.clear(0, 0, 0, 1);
	
		// If user presses L, go back to menu.
		if (Gdx.input.isKeyPressed(Keys.L)) {
			
			game.setScreen(new MenuScreen(game));
			
		}

		// score system
		// Start drawing textures/text to screen
		game.batch.begin();
		game.font.setColor(Color.WHITE);
		game.font.getData().setScale(4);
		
		// If either player has a score of 5, they win
		if (playerOneScore == 5) {

			// Player one text.
			game.font.getData().setScale(2);
			game.font.draw(game.batch, "PLAYER ONE WINS! \n'ESCAPE' TO PLAYER AGAIN\n'L' FOR MENU SCREEN", TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - TwoPlayerPong.GAME_HEIGHT / 4);
			resetGame();
			
		} else if (playerTwoScore == 5) {
			
			// Player two text
			game.font.getData().setScale(2);
			game.font.draw(game.batch, "PLAYER TWO WINS! \n'ESCAPE' TO PLAYER AGAIN\n'L' TO LEAVE", TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - TwoPlayerPong.GAME_HEIGHT / 4);
			resetGame();
			
		}
		
		game.font.setColor(Color.WHITE);
		game.font.getData().setScale(4);
		game.font.draw(game.batch, String.valueOf(playerOneScore), TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - 20);
		game.font.draw(game.batch, String.valueOf(playerTwoScore), TwoPlayerPong.GAME_WIDTH - TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - 20);
		
		// If first time on screen, show instructions
		if (initialStart) {
			
			game.font.getData().setScale(2);
			game.font.draw(game.batch, "FIRST TO 5 WINS!", TwoPlayerPong.GAME_WIDTH / 4 + 30, TwoPlayerPong.GAME_HEIGHT / 4);
			game.font.getData().setScale(1);
			game.font.draw(game.batch, "USE 'W' FOR UP AND 'S' FOR PLAYER ONE.\nUSE UP AND DOWN ARROWS FOR PLAYER TWO \n'SPACE' FOR CRAZY MODE. ENJOY!\nPRESS 'ESCAPE' TO START.\n'L' FOR MENU SCREEN", TwoPlayerPong.GAME_WIDTH / 4 + 30, TwoPlayerPong.GAME_HEIGHT / 5);
			
		}
		
		// end drawing
		game.batch.end();
		
		// If game has started
		if (hasStarted) {
			
			initialStart = false; // Game has started
		
			// Change x and y position with the Corresponding velocity multiplied by delta time to assure framerate doesnt affect speed of ball.
			circleX += circleVelX * Gdx.graphics.getDeltaTime();
			circleY += circleVelY * Gdx.graphics.getDeltaTime();
			
			// If ball is touching either paddle or bot paddle, change ball X velocity by *-1 to reverse direction.
			if (circleX + CIRCLE_RADIUS + PIXEL_BUFFER > TwoPlayerPong.GAME_WIDTH || circleX - CIRCLE_RADIUS - PIXEL_BUFFER < 0 || playerTwoPaddle.contains(circleX + PIXEL_BUFFER + CIRCLE_RADIUS / 2 + PADDLE_WIDTH / 2, circleY) || playerOnePaddle.contains(circleX - PIXEL_BUFFER - CIRCLE_RADIUS / 2 - PADDLE_WIDTH / 2, circleY)) {
				
				game.ballBounceSound.play(1.0f);
				circleVelX *= -1;
				
			}
			
			// If ball is touching the bottom or top wall, change ball Y velocity by *-1 to reverse direction.
			if (circleY + PIXEL_BUFFER + CIRCLE_RADIUS > TwoPlayerPong.GAME_HEIGHT || circleY - PIXEL_BUFFER - CIRCLE_RADIUS < 0) {
				
				game.ballBounceSound.play(1.0f);
				circleVelY *= -1;
				 
			}
			
		
			// If W pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.W) && playerOnePaddle.getY() + playerOnePaddle.getHeight() < TwoPlayerPong.GAME_HEIGHT) {
				
				// Raise paddle y by PADDLE_SPEED Up
				playerOnePaddle.y += Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If S pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.S) && playerOnePaddle.getY() > 0) {
				
				// Raise paddle y by PADDLE_SPEED Down
				playerOnePaddle.y -= Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If Up pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.UP) && playerTwoPaddle.getY() + playerTwoPaddle.getHeight() < TwoPlayerPong.GAME_HEIGHT) {
				
				// Raise paddle y by PADDLE_SPEED 
				playerTwoPaddle.y += Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If Down pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.DOWN) && playerTwoPaddle.getY() > 0) {
				
				// Raise paddle y by PADDLE_SPEED Down
				playerTwoPaddle.y -= Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If escape key pressed, start game
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				
				resetGame();
				startGame();
				
			}
			
			// If ball touches left wall, raise player two score by 1
			if (circleX - PIXEL_BUFFER - CIRCLE_RADIUS <= 0) {
				
				playerTwoScore++;
				resetGame();
				
			}
			
			// If ball touches right wall, raise playerOne score by 1
			if (circleX + CIRCLE_RADIUS + PIXEL_BUFFER >= TwoPlayerPong.GAME_WIDTH) {
				
				playerOneScore++;
				resetGame();
			
			}
			
		}
		
		// Begin drawing shapes with shape renderer
		game.shapeRenderer.begin(ShapeType.Filled);
		
		// Set color and draw paddles
		game.shapeRenderer.setColor(Color.WHITE);
		game.shapeRenderer.rect(playerOnePaddle.x, playerOnePaddle.y, playerOnePaddle.width, playerOnePaddle.height);
		game.shapeRenderer.rect(playerTwoPaddle.x, playerTwoPaddle.y, playerTwoPaddle.width, playerTwoPaddle.height);
		
		// Render pong ball
		// If space is pressed, ball speed doubles and changes colors
		if (Gdx.input.isKeyPressed(Keys.SPACE) && hasStarted) {
			// BOOST SPEED AND CHANGE COLOR
			// moves double as fast since we are adding to it twice in one frame
			circleX += circleVelX * Gdx.graphics.getDeltaTime();
			circleY += circleVelY * Gdx.graphics.getDeltaTime();
			
			// draw pong ball
			game.shapeRenderer.setColor(game.random.nextFloat(), game.random.nextFloat(), game.random.nextFloat(), 1);
			game.shapeRenderer.circle(circleX, circleY, pongBall.radius);
			
		} else {
		
			// Normal speed and color
			game.shapeRenderer.setColor(Color.BLUE);
			game.shapeRenderer.circle(circleX, circleY, pongBall.radius);
		
		}
		
		// end rendering shapes
		game.shapeRenderer.end();
		
		// Escape pressed, start game
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			
			startGame();
			
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	// Reset game method to reset location and variables back to normal
	public void resetGame() {
		
		//playerOnePaddle.setX(50);
		playerOnePaddle.setY(TwoPlayerPong.GAME_HEIGHT/2 - PADDLE_HEIGHT/2);
		playerTwoPaddle.setY(TwoPlayerPong.GAME_HEIGHT/2 - PADDLE_HEIGHT/2);
		circleX = TwoPlayerPong.GAME_WIDTH / 2;
		circleY = TwoPlayerPong.GAME_HEIGHT / 2;
		hasStarted = false;
		
	}
	
	// Start game method, change variables and check if either score has hit 5.
	public void startGame() {
		
		hasStarted = true;
		
		if (playerOneScore == 5 || playerTwoScore == 5) {
			
			playerOneScore = 0;
			playerTwoScore = 0;
			
		}
		
	}
	
	

}
