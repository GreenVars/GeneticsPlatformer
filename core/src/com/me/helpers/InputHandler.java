package com.me.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.me.objects.Player;
import com.me.world.PlatformRenderer;
import com.me.world.PlatformWorld;

public class InputHandler implements InputProcessor {
	static final boolean DEV_MODE = true;
	private PlatformWorld world;
	private Player player;
	private ArrayList<Player> players;
	private PlatformRenderer render;
	public InputHandler(PlatformWorld world, PlatformRenderer render) {
		this.world = world;
		this.render = render;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(Constants.PRINT_INPUT)
			Gdx.app.log("Touch Down", screenX + " , " + screenY + " , " + button);
		return true;
	}
	public void PlayerKeyDown(int keycode, Player p) {
		player = p;
		keyDown(keycode);
	}
	@Override
	public boolean keyDown(int keycode) {
		if(Constants.PRINT_INPUT)
			Gdx.app.log("Key Down", "" + keycode);
		if(player != null) {
			switch(keycode) {
			// Player Controls
			case Keys.W:
				player.jump();
				break;
			case Keys.A:
				player.moveLeft();
				break;
			case Keys.D:
				player.moveRight();
				break;
			case Keys.S:
				//player.crouch();
				break;
				// Camera Controls
			case Keys.M:
				world.getLevel().setMainPlayer(world.getLevel().index + 1);
				break;
			case Keys.N:
				world.getLevel().setMainPlayer(world.getLevel().index - 1);
				break;
			case Keys.MINUS:
				render.zoomingIn = true;
				break;
			case Keys.EQUALS:
				render.zoomingOut = true;
				break;
			case Keys.LEFT:
				//render.getCam().translate(new Vector2(-2, 0));
				break;
			case Keys.RIGHT:
				//render.getCam().translate(new Vector2(2, 0));
				break;
			case Keys.UP:
				//render.getCam().translate(new Vector2(0 , 2));
				break;
			case Keys.DOWN:
				//render.getCam().translate(new Vector2(0, -2));
				break;
				// Console Controls	
			case Keys.V:
				Gdx.app.log("Player Velocity", "" + player.getVelocity());
				break;
			case Keys.P:
				Gdx.app.log("Player Position", "" + player.getPosition());
				break;
			}
		}
		return true;
	}
	public void PlayerKeyUp(int key, Player player2) {
		player = player2;
		keyDown(key);
	}



	@Override
	public boolean keyUp(int keycode) {
		if(Constants.PRINT_INPUT)
			Gdx.app.log("Key Up", "" + keycode);
		switch(keycode) {
		case Keys.A:
			player.normalizeX();
			break;
		case Keys.D:
			player.normalizeX();
			break;
		case Keys.S:
			//player.standUp();
			break;
		case Keys.MINUS:
			render.zoomingIn = false;
			break;
		case Keys.EQUALS:
			render.zoomingOut = false;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
