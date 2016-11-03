package com.me.helpers;

import com.badlogic.gdx.math.Vector2;

public class Constants {
	// Console Printing
	public static final boolean PRINT_INPUT = false;
	public static final boolean PRINT_GENE_STREAM = false;

	// World Attributes
	public static final float GRAVITY_X = 0;
	public static final float GRAVITY_Y = -98f;
	
	// Box2D.World.step parameters
	public static final float TIME_STEP = 1/60f;
	public static final int VELOCITY_ITERATIONS = 4; // Arbitrary
	public static final int POSITION_ITERATIONS = 4; // Arbitrary
	
	// Default FixtureDef Attributes
	public static final float FRICTION = 10f;
	public static final float RESTITUTION = .2f;
	public static final float DENSITY = 8f;
	
	// Default BodyDef Attributes
	public static final boolean ACTIVE = true;
	public static final float ANGLE = (float) (Math.PI * 0f); //in radians
	public static final float ANGULAR_VELOCITY = 0; // delta theta / step
	public static final boolean BULLET = true;
	public static final float GRAVITY_SCALE = 1;
	public static final float LINEAR_DAMPING = 1f;
	public static final Vector2 LINEAR_VELOCITY = new Vector2(0, 0);
	
	// Players
	public static int PLAYER_AMT = 64;
	public static final short PLAYER_BITS = 0x0001;
	public static final short SCENERY_BITS = 0x0002;
	public static final float X_FORCE = 10000;
	public static final float Y_FORCE = 10000;
	public static final float MAX_X_VELOCITY = 50;
	public static final float MAX_Y_VELOCITY = 300;
	public static final float PLAYER_DAMPING = .4f;
	// Camera Effects
	public static final float ZOOM_INC = .05f;
	
	// Gene Variables
	public static final float SECONDS_MAX = 1f;
	public static final float SECONDS_MIN = 0;
	public static final float TIME_MUTATE_CHANCE = .5f; //Key mutate chance = 1 - this
	public static final float KEY_MUTATE_CHANCE = .1f; //For Gene.crossover
	
	// Sequence Variables
	public static final int SEQUENCE_START_LENGTH = 30;
	public static final float SEQUENCE_MUTATE_CHANCE = .3f;
	
	// Population Variables
	public static final int POPULATION_START_LENGTH = PLAYER_AMT; // Multiple of 2

}
