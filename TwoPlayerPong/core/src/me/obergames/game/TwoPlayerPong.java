package me.obergames.game;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.obergames.screens.MenuScreen;

public class TwoPlayerPong extends Game {
	
	
	// constants
	public static final double versionNumber = 1;
	public static final int GAME_HEIGHT = 600; // window height
	public static final int GAME_WIDTH = 600; // window width
	
	// declare variables to use in Scene classes
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	public Random random;
	public Sound ballBounceSound;
	public Music backgroundMusic;
	
	
	// Game is opened
	@Override
	public void create () {

		// instantiate variables when game started.
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		random = new Random();
		
		// audio
		ballBounceSound = Gdx.audio.newSound(Gdx.files.internal("ballBounceSound.ogg")); 
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3"));
		backgroundMusic.setLooping(true); // loop continuously
		backgroundMusic.play(); // start playing
		
		// Set current screen to the MenuScreen and pass this current class instance to the constructor.
		this.setScreen(new MenuScreen(this));
	
	} 

	@Override
	public void render () {
		
		// Use the parent objects render method
		super.render();
		
	}
	
	@Override
	public void dispose () {
		
		// Dispose of our objects - no memory leaks.
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
		ballBounceSound.dispose();
		backgroundMusic.dispose();
		
	}
}
