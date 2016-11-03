package com.me.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.me.genetics.Population;
import com.me.helpers.InputHandler;
import com.me.helpers.PopulationReader;

public class PlatformScreen implements Screen {
	private float runtime = 0;
	private PlatformRenderer render;
	private PlatformWorld world;
	public InputHandler input;
	private PopulationReader popReader;
	public PlatformScreen() {
		world = new PlatformWorld();
		render = new PlatformRenderer(world);
		input = new InputHandler(world, render);
		Gdx.input.setInputProcessor(input);
		popReader = new PopulationReader(new Population(world),
										 input,
										 world);
	}
	@Override
	public void render(float delta) {
		runtime += delta;
		render.render(delta);
		world.update(delta);
		popReader.run();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("Resize", width + " , " + height);		
	}
	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
