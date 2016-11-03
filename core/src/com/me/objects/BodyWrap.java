package com.me.objects;

import com.badlogic.gdx.Gdx;
import com.me.helpers.Constants;
import com.me.world.PlatformRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
public class BodyWrap {
	protected float initX, initY;
	protected int radius, width, height;
	protected World physicsWorld;
	protected BodyDef bDef;
	protected Body body;
	protected Fixture fixture;
	protected FixtureDef fDef;
	protected Shape.Type sType;
	protected PolygonShape box;
	protected CircleShape circle;
	protected Rectangle boundingBox = new Rectangle(-100,-100,2000,2000);
	public BodyWrap(World physicsWorld, BodyType type, int x, int y) {
		this.physicsWorld = physicsWorld;
		this.initX = x;
		this.initY = y;
		bDef = new BodyDef();
		bDef.type = type;
		bDef.position.set(initX, initY);
		bDef.active = Constants.ACTIVE;
		bDef.angle = Constants.ANGLE;
		bDef.bullet = Constants.BULLET;
		bDef.angularVelocity = Constants.ANGULAR_VELOCITY;
		bDef.gravityScale = Constants.GRAVITY_SCALE;
		bDef.linearDamping = Constants.LINEAR_DAMPING;
		bDef.linearVelocity.set(Constants.LINEAR_VELOCITY);
		fDef = new FixtureDef();
		fDef.density = Constants.DENSITY;
		fDef.friction = Constants.FRICTION;
		fDef.restitution = Constants.RESTITUTION;
		fDef.filter.categoryBits = Constants.SCENERY_BITS;
	}
	public BodyWrap(World physicsWorld, int x, int y) {
		this(physicsWorld, BodyType.StaticBody, x, y);
	}
	public void setBodyDef(boolean active, float angle, float angV, 
						   boolean bullet, float gScale, Vector2 linV) {
		bDef.active = active;
		bDef.angle = angle;
		bDef.angularVelocity = angV;
		bDef.bullet = bullet;
		bDef.gravityScale = gScale;
		bDef.linearVelocity.set(linV);
	}
	public void setFixtureDef(float density, float friction, float restitution) {
		fDef.density = density;
		fDef.friction = friction;
		fDef.restitution = restitution;
	}
	public void setAsBox(int width, int height) {
		sType = Shape.Type.Polygon;
		this.width = width;
		this.height = height;
		box = new PolygonShape();
		box.setAsBox(width, height);
		fDef.shape = box;
	}
	public void setAsCircle(int radius) {
		sType = Shape.Type.Circle;
		this.radius = radius;
		circle = new CircleShape();
		circle.setRadius(radius);
		fDef.shape = circle;
	}
	public void setDynamic() {
		bDef.type = BodyType.DynamicBody;
	}
	public void setKinetic() {
		bDef.type = BodyType.KinematicBody;
		fDef.restitution = 0;
	}
	public void setStatic() {
		bDef.type = BodyType.StaticBody;
	}
	public void setBoundingBox(int x, int y, int width, int height) {
		boundingBox = new Rectangle(x, y, width, height);
	}
	public void setBoundingBox(Rectangle box) {
		boundingBox = box;
	}
	public void checkBounds() {
		Vector2 currentPos = body.getPosition();
		if(currentPos.x > boundingBox.x + boundingBox.width) {
			currentPos.x = boundingBox.x + boundingBox.width;
			body.setLinearVelocity(-body.getLinearVelocity().x,
					   				body.getLinearVelocity().y);
		} else if (currentPos.x < boundingBox.x) {
			currentPos.x = boundingBox.x;
			body.setLinearVelocity(-body.getLinearVelocity().x,
	   								body.getLinearVelocity().y);
		}
		if(currentPos.y > boundingBox.y + boundingBox.height) {
			currentPos.y = boundingBox.y + boundingBox.height;
			body.setLinearVelocity(  body.getLinearVelocity().x,
					   				-body.getLinearVelocity().y);
		} else if (currentPos.y < boundingBox.y) {
			currentPos.y = boundingBox.y;
			body.setLinearVelocity(  body.getLinearVelocity().x,
	   						    	-body.getLinearVelocity().y);
		}
		
	}
	public void setLinV(Vector2 v) {
		bDef.linearVelocity.set(v);
	}

	public void create() {
		body = physicsWorld.createBody(bDef);
		fixture = body.createFixture(fDef);
		log();
		switch(sType) {
		case Circle:
			circle.dispose();
			break;
		case Polygon:
			box.dispose();
			break;
		case Chain:
			break;
		case Edge:
			break;
		default:
			Gdx.app.log("Error", "Body has unhandled Shape.Type");
			break;
		}
	}
	public void render(PlatformRenderer r) {
		//r.shapeR.setColor(.2f, .9f, .3f, 1);	
	}
	public BodyDef getBodyDef() {
		return bDef;
	}
	public FixtureDef getFixtureDef() {
		return fDef;
	}
	public Body getBody() {
		return body;
	}
	public Vector2 getPosition() {
		return body.getPosition();
	}
	public Vector2 getVelocity() {
		return body.getLinearVelocity();
	}
	public World getWorld() {
		return physicsWorld;
	}
	public void log() {
		String dimensions = sType == Shape.Type.Circle ? 
				"Radius: " + radius : "Width: " + width + " Height: " + height;
		Gdx.app.log("Body", getPosition() + " " + dimensions);
	}
	public Shape.Type getShapeType() {
		return fDef.shape.getType();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getRadius() {
		return radius;
	}
}
