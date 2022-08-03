package me.obergames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import me.obergames.game.TwoPlayerPong;

public class OnePlayerGameScreen implements Screen {

	// Declare variables/objects
	private TwoPlayerPong game;
	private Rectangle playerPaddle, botPaddle;
	private Circle pongBall;
	float circleVelX;
	float circleVelY;
	float botPaddleSpeed;
	float targetY;
	
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
	private int playerScore = 0; // 
	private int botScore = 0;

	// OnePlayerGameScreen Constructor - parameter to pass Game instance
	public OnePlayerGameScreen (TwoPlayerPong game) {
		
		this.game = game;
		
		// x , y , width, height
		playerPaddle = new Rectangle(50, TwoPlayerPong.GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		botPaddle = new Rectangle(TwoPlayerPong.GAME_WIDTH - PADDLE_WIDTH - 50, TwoPlayerPong.GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, PADDLE_WIDTH, PADDLE_HEIGHT);
		
		// x, y, radius
		// Create circle object to use for our pong ball
		pongBall = new Circle(circleX, circleY, CIRCLE_RADIUS);
		
		// Switch sides the ball goes to every time.
		// Assign variables
		circleVelX = game.random.nextInt(100) + 300;
		circleVelY = 115;
		botPaddleSpeed = 100;
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
		
		// If player has a score of 5, user wins.
		if (playerScore == 5) {

			// You win text.
			game.font.draw(game.batch, "YOU WIN!", TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - TwoPlayerPong.GAME_HEIGHT / 4);
			resetGame(); 
			
		} else if (botScore == 5) {
			
			// Bot wins text
			game.font.draw(game.batch, "BOT WINS :(", TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - TwoPlayerPong.GAME_HEIGHT / 4);
			resetGame();
			
		}
		
		game.font.setColor(Color.WHITE);
		game.font.getData().setScale(4); // Set font scale to 4
		
		// Draw 
		game.font.draw(game.batch, String.valueOf(playerScore), TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - 20);
		game.font.draw(game.batch, String.valueOf(botScore), TwoPlayerPong.GAME_WIDTH - TwoPlayerPong.GAME_WIDTH / 4, TwoPlayerPong.GAME_HEIGHT - 20);
		
		// If first time on screen, show instructions
		if (initialStart) {
			
			game.font.getData().setScale(2);
			game.font.draw(game.batch, "FIRST TO 5 WINS!", TwoPlayerPong.GAME_WIDTH / 4 + 30, TwoPlayerPong.GAME_HEIGHT / 4);
			game.font.getData().setScale(1);
			game.font.draw(game.batch, "USE 'W' FOR UP AND 'S' FOR DOWN.\n 'SPACE' FOR CRAZY MODE. ENJOY!\n PRESS 'ESCAPE' TO START.", TwoPlayerPong.GAME_WIDTH / 4 + 30, TwoPlayerPong.GAME_HEIGHT / 5);
			
		}
		
		// end drawing
		game.batch.end();
		
		// If game has started
		if (hasStarted) {
			
			initialStart = false; // Game has started
		
			// Change x and y position with the Corresponding velocity multiplied by delta time to assure framerate doesnt affect speed of ball.
			circleX += circleVelX * Gdx.graphics.getDeltaTime();
			circleY += circleVelY * Gdx.graphics.getDeltaTime();
			
			// If circle is touching paddle 
			if ((botPaddle.y >= 0 || circleY >= PADDLE_HEIGHT / 2) && (botPaddle.y + PADDLE_HEIGHT <= TwoPlayerPong.GAME_HEIGHT || circleY <= TwoPlayerPong.GAME_HEIGHT - PADDLE_HEIGHT / 2)) { 
				
				targetY = circleY - PADDLE_HEIGHT / 2;
				
				// circle above paddle
				if (targetY > botPaddle.y) {
					
					botPaddle.y += botPaddleSpeed * Gdx.graphics.getDeltaTime();
					
				} else if (targetY < botPaddle.y) {
					
					botPaddle.y -= botPaddleSpeed * Gdx.graphics.getDeltaTime();
					
				}
				
			}
			
			// If ball is touching either paddle, change ball X velocity by *-1 to reverse direction.
			if (circleX + CIRCLE_RADIUS + PIXEL_BUFFER > TwoPlayerPong.GAME_WIDTH || circleX - CIRCLE_RADIUS - PIXEL_BUFFER < 0 || botPaddle.contains(circleX + CIRCLE_RADIUS / 2 + PADDLE_WIDTH / 2 + PIXEL_BUFFER, circleY) || playerPaddle.contains(circleX - CIRCLE_RADIUS / 2 - PADDLE_WIDTH / 2 - PIXEL_BUFFER, circleY)) {
				
				game.ballBounceSound.play(1.0f);
				circleVelX *= -1;
				
			}
			
			// If ball is touching the bottom or top wall, change ball Y velocity by *-1 to reverse direction.
			if (circleY + PIXEL_BUFFER + CIRCLE_RADIUS > TwoPlayerPong.GAME_HEIGHT || circleY - PIXEL_BUFFER - CIRCLE_RADIUS < 0) {
				
				game.ballBounceSound.play(1.0f);
				circleVelY *= -1;
				 
			}
		
			// If W pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.W) && playerPaddle.getY() + playerPaddle.getHeight() < TwoPlayerPong.GAME_HEIGHT) {
				
				// Raise paddle y by PADDLE_SPEED Up
				playerPaddle.y += Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If S pressed and paddle is still in bounds
			if (Gdx.input.isKeyPressed(Keys.S) && playerPaddle.getY() > 0) {
				
				// Raise paddle y by PADDLE_SPEED Down
				playerPaddle.y -= Gdx.graphics.getDeltaTime() * PADDLE_SPEED;
				
			}
			
			// If escape key pressed, start game
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				
				resetGame();
				startGame();
				
			}
			
			// If ball touches left wall, raise bot score by 1
			if (circleX - CIRCLE_RADIUS - PIXEL_BUFFER <= 0) {
				
				botScore++;
				resetGame();
				
			}
			
			// If ball touches right wall, raise player score by 1
			if (circleX + CIRCLE_RADIUS + PIXEL_BUFFER >= TwoPlayerPong.GAME_WIDTH) {
				
				playerScore++;
				resetGame();
			
			}
			
		}
		
		// Begin drawing shapes with shape renderer
		game.shapeRenderer.begin(ShapeType.Filled);
		
		// Set color and draw paddles
		game.shapeRenderer.setColor(Color.WHITE);
		game.shapeRenderer.rect(playerPaddle.x, playerPaddle.y, playerPaddle.width, playerPaddle.height);
		game.shapeRenderer.rect(botPaddle.x, botPaddle.y, playerPaddle.width, playerPaddle.height);
		
		// Render pong ball
		// If space is pressed, ball speed doubles and changes colors
		if (Gdx.input.isKeyPressed(Keys.SPACE) && hasStarted) {
			// BOOST SPEED AND CHANGE COLOR
			// moves double as fast since we are adding to it twice in one frame
			circleX += circleVelX * Gdx.graphics.getDeltaTime();
			circleY += circleVelY * Gdx.graphics.getDeltaTime();
			botPaddleSpeed = 190;
			
			// draw pong ball
			game.shapeRenderer.setColor(game.random.nextFloat(), game.random.nextFloat(), game.random.nextFloat(), 1);
			game.shapeRenderer.circle(circleX, circleY, pongBall.radius);
			
		} else {
		
			// Normal speed and color
			botPaddleSpeed = 100;
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
	
		playerPaddle.setX(50);
		playerPaddle.setY(TwoPlayerPong.GAME_HEIGHT/2 - PADDLE_HEIGHT/2);
		botPaddle.setY(TwoPlayerPong.GAME_HEIGHT/2 - PADDLE_HEIGHT/2);
		circleX = TwoPlayerPong.GAME_WIDTH / 2;
		circleY = TwoPlayerPong.GAME_HEIGHT / 2;
		hasStarted = false;
		
	}
	
	// Start game method, change variables and check if either score has hit 5.
	public void startGame() {
		
		hasStarted = true;
		
		if (playerScore == 5 || botScore == 5) {
			
			playerScore = 0;
			botScore = 0;
			
		}
		
	}
	
	
	

}
