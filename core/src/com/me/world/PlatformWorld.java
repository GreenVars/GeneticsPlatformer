package com.me.world;

import java.util.ArrayList;

import com.me.helpers.Constants;
import com.me.objects.BodyWrap;
import com.me.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
public class PlatformWorld {
	private Player player;
	private World physicsWorld;
	private PlatformLevel level;
	private ArrayList<Player> players;
	public PlatformWorld() {
		this.physicsWorld = new World(new Vector2(
				Constants.GRAVITY_X, Constants.GRAVITY_Y), true);
		level = new PlatformLevel(physicsWorld);
		level.create("level2.lvl");
		player = level.getMainPlayer();
		players = level.getPlayers();
	}
	public void update(float delta) {
		physicsWorld.step(Constants.TIME_STEP, 
				Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
		for(Player p: players) {
			if(p.movingLeft) {
				p.moveLeft();
			} else if(p.movingRight) {
				p.moveRight();
			}
			//if(p.crouching) {
				//p.crouch();
			//}
		}
		for(BodyWrap b: level.getPieces()) {
			b.checkBounds();
		}
	}

	public Player getPlayer() {
		return player;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public World getPhysicsWorld() {
		return physicsWorld;
	}
	public PlatformLevel getLevel() {
		return level;
	}
	public void addPlayers(ArrayList<Player> players2) {
		// XXX TODO CLEAR BOX2D BODIES AND REPLACE WITH THIS
		for(Player p: players2) {
			p.getBodyDef().linearDamping = Constants.PLAYER_DAMPING;
			p.setAsCircle(2);
			p.setPieces(level.getPieces());
			p.create();
		}
	}
}
