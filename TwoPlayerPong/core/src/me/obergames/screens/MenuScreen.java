package me.obergames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import me.obergames.game.TwoPlayerPong;

public class MenuScreen implements Screen {
	
	// Private variables
	private TwoPlayerPong game; // Game class
	private Texture pongImage, onePlayerImage, twoPlayerImage; // Declare texture variables
	private float menuX;
	private float menuY; 
	private Rectangle outerRect, outerRect2;
	
	// Constants to use to space out menu
	private final int SMALL_SPACE = 50;
	private final int LARGE_SPACE = 100;
	
	// MenuScreen constructor with a parameter that allows us to pass in the Game class Instance
	public MenuScreen (TwoPlayerPong game) {
		
		this.game = game;
		
	}
	
	
	// Scene is shown
	@Override
	public void show() {
		
		// Initialize textures
		pongImage = new Texture("pongImage.png");
		onePlayerImage = new Texture("onePlayerImage.png");
		twoPlayerImage = new Texture("twoPlayerImage.png");
		
	
		// Common x and y used to place accordingly
		menuX = (TwoPlayerPong.GAME_WIDTH - pongImage.getWidth()) / 2;
		menuY = TwoPlayerPong.GAME_HEIGHT / 2;
		
		// Create rectangle objects (x, y, width, height)
		outerRect = new Rectangle(menuX, menuY - SMALL_SPACE, pongImage.getWidth(), 75);
		outerRect2 = new Rectangle(menuX, menuY - LARGE_SPACE - SMALL_SPACE, pongImage.getWidth(), 75);
	}

	
	// Render method (called 60 times a second) 
	@Override
	public void render(float delta) {
		
		// Clear screen to black
		ScreenUtils.clear(0, 0, 0, 1);
		
		// Begin drawing textures/text with batch.
		game.batch.begin();  
		
		game.font.getData().setScale(1); // Text size normal
		game.batch.draw(onePlayerImage, menuX + 25, menuY - SMALL_SPACE + 17, 200, 40);
		game.batch.draw(pongImage, menuX, menuY + LARGE_SPACE);
		game.batch.draw(twoPlayerImage, menuX + 25, menuY - SMALL_SPACE - LARGE_SPACE + 17, 200, 40);
		game.font.setColor(Color.CYAN);
		game.font.draw(game.batch, "Music by David Fesliyan!", 10, 20);
		game.font.setColor(game.random.nextFloat(), game.random.nextFloat(), game.random.nextFloat(), 1); // Random color using Random class
		game.font.draw(game.batch, "Made By Evan Oberneder", menuX, menuY + LARGE_SPACE);
		
		// End drawing
		game.batch.end();
		
		// Begin drawing rectangles with ShapeRenderer
		game.shapeRenderer.begin(ShapeType.Line);
		// rect (x, y, width, height)
		game.shapeRenderer.setColor(Color.YELLOW);
		game.shapeRenderer.rect(outerRect.getX(), outerRect.getY(), outerRect.getWidth(), outerRect.getHeight());
		game.shapeRenderer.setColor(Color.RED);
		game.shapeRenderer.rect(outerRect2.getX(), outerRect2.getY(), outerRect2.getWidth(), outerRect2.getHeight());
		
		// Check if mouse position is over "One Player" Button
		
		// Check X
		if (Gdx.input.getX() >= menuX && Gdx.input.getX() <= menuX + pongImage.getWidth()) {
			
			// Check Y
			if (Gdx.input.getY() >= menuY + SMALL_SPACE - outerRect.getHeight() && Gdx.input.getY() <= menuY + SMALL_SPACE) {
				
				game.shapeRenderer.setColor(Color.YELLOW);
				game.shapeRenderer.rect(menuX + 10, menuY - SMALL_SPACE + 10, pongImage.getWidth() - 20, 55); // Create another rectangle to show User they are hovering over button.
				
				// If mouse clicked
				if (Gdx.input.isTouched()) {
					
					// change to game screen
					game.setScreen(new OnePlayerGameScreen(game));
					
				}
				
			}
			
		}
		
		
		// Check if mouse is hovering over "Two Player" button
		
		// Check X
		if (Gdx.input.getX() >= menuX && Gdx.input.getX() <= menuX + pongImage.getWidth()) {
			
			// Check Y
			if (Gdx.input.getY() >= menuY + SMALL_SPACE + LARGE_SPACE - outerRect.getHeight() && Gdx.input.getY() <= menuY + SMALL_SPACE + LARGE_SPACE) {
				
				game.shapeRenderer.setColor(Color.RED);
				game.shapeRenderer.rect(menuX + 10, menuY - SMALL_SPACE - LARGE_SPACE + 10, pongImage.getWidth() - 20, 55);
				
				if (Gdx.input.isTouched()) {
					
					game.setScreen(new TwoPlayerGameScreen(game));
					
				}
				
			}
			
		}
		
		// End shapeRenderer to ensure shapes are drawn correctly
		game.shapeRenderer.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
		//gamePort.update(width, height);
		
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
	
	
	

}
