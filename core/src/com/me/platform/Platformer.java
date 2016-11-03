package com.me.platform;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.me.world.PlatformScreen;

public class Platformer extends Game {

	@Override
	public void create() {
		Gdx.app.log("Event", "Platformer Created");
		setScreen(new PlatformScreen());
		
	}
	@Override
	public void dispose() {
		super.dispose();
	}
}
