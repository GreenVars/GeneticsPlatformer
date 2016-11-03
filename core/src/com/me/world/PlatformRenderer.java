package com.me.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.me.helpers.Constants;

public class PlatformRenderer {
	private PlatformWorld world;
	private OrthographicCamera cam;
	private SpriteBatch batcher;
	private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	public ShapeRenderer shapeR;
	public Boolean zoomingIn = false;
	public Boolean zoomingOut = false;
	public PlatformRenderer(PlatformWorld world) {
		this.world = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 150, 150); // Normal cartesian cordinates
		// SPRITE BATCHER HERE
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeR = new ShapeRenderer();
		shapeR.setProjectionMatrix(cam.combined);
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(zoomingIn) {
			cam.zoom += Constants.ZOOM_INC;
		}
		if(zoomingOut) {
			cam.zoom -= Constants.ZOOM_INC;
		}
		cam.position.set(world.getLevel().getMainPlayer().getPosition(),
						 0);
		cam.update();
		debugger.render(world.getPhysicsWorld(), cam.combined);
	}
	public OrthographicCamera getCam() {
		return cam;
	}
}
