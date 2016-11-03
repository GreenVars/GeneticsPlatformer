package com.me.objects;

import java.util.ArrayList;

import com.me.helpers.Constants;
import com.me.world.PlatformRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
public class Player extends BodyWrap {
	public boolean movingLeft = false;
	public boolean movingRight = false;
	public boolean crouching = false;
	private ArrayList<BodyWrap> pieces;
	public Player(World physicsWorld, int x, int y) {
		super(physicsWorld, BodyType.DynamicBody, x, y);
		this.fDef.filter.categoryBits = Constants.PLAYER_BITS;
		this.fDef.filter.maskBits = Constants.SCENERY_BITS;
	}
	public Player(World physicsWorld) {
		super(physicsWorld, BodyType.DynamicBody, 0, 0);
		this.fDef.filter.categoryBits = Constants.PLAYER_BITS;
		this.fDef.filter.maskBits = Constants.SCENERY_BITS;
	}
	public void setPieces(ArrayList<BodyWrap> parts) {
		pieces = parts;
	}
	@Override
	public void render(PlatformRenderer r) {
		r.shapeR.setColor(.8f, .2f, .1f, 1);
	}
	public void moveLeft() {
		movingLeft = true;
		//body.applyLinearImpulse(
		//	new Vector2(-Constants.X_FORCE, 0), getPosition(), true);
		if(body.getLinearVelocity().x > -Constants.MAX_X_VELOCITY)
			body.applyForceToCenter(new Vector2(-Constants.X_FORCE, 0), true);
	}
	public void moveRight() {
		movingRight = true;
		//body.applyLinearImpulse(
		//	new Vector2(Constants.X_FORCE, 0), getPosition(), true);
		if(body.getLinearVelocity().x < Constants.MAX_X_VELOCITY)
			body.applyForceToCenter(new Vector2(Constants.X_FORCE, 0), true);
		else {
			body.setLinearVelocity(Constants.MAX_X_VELOCITY, 
					body.getLinearVelocity().y);
		}
	}
	public void normalizeX() {
		movingLeft = false;
		movingRight = false;
	}
	public void crouch() {
		//crouching = true;
		//if(body.getLinearVelocity().y > -Constants.MAX_Y_VELOCITY)
		//body.applyForceToCenter(new Vector2(0, -Constants.Y_FORCE), true);
	}
	public void standUp() {
		//crouching = false;
	}

	public void jump() {
		//body.applyForceToCenter(0, 10*Constants.Y_FORCE, true);
		//body.applyLinearImpulse(0, Constants.Y_FORCE, bDef.position.x, bDef.position.y, true);
		if(isGrounded()) {
			body.applyLinearImpulse(new Vector2(0, Constants.Y_FORCE), body.getWorldCenter(), true);
		}
	}
	public boolean isGrounded() {
		for(BodyWrap p: pieces) {
			float pCeiling = p.getPosition().y + (p.getShapeType() == Shape.Type.Circle ? 
					p.getRadius() : p.getHeight());
			boolean inPWidth = getPosition().x > p.getPosition().x - p.getWidth() &&
					getPosition().x < p.getPosition().x + p.getWidth();
			if(Math.abs(getPosition().y - getRadius() - pCeiling) < 1f) {
				if(inPWidth) {
					return true;
				}
			}
		}
		return false;
	}
}
