package com.me.world;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.helpers.Constants;
import com.me.objects.BodyWrap;
import com.me.objects.Player;

public class PlatformLevel {
	private ArrayList<BodyWrap> pieces;
	private ArrayList<Player> players;
	private BodyWrap leftWall;
	private BodyWrap rightWall;
	private BodyWrap floor;
	private BodyWrap ceiling;
	private World world;
	private Player mainPlayer;
	public int index = 0;
	public PlatformLevel(World world) {
		this.world = world;
		pieces = new ArrayList<BodyWrap>();
		players = new ArrayList<Player>();
		for(int i = 0; i < Constants.PLAYER_AMT; i++) {
			players.add(new Player(world, 20, 20));
		}
		mainPlayer = players.get(index);
		floor = new BodyWrap(world, BodyType.StaticBody, 
				0, 0);
		leftWall = new BodyWrap(world, BodyType.StaticBody, 
				0, 0);
		rightWall = new BodyWrap(world, BodyType.StaticBody,
				Gdx.graphics.getWidth(), 0);
		ceiling = new BodyWrap(world, BodyType.StaticBody, 
				0, Gdx.graphics.getHeight());
	}
	public void create(String levelName) {
		floor.setAsBox(Gdx.graphics.getWidth(), 5);
		leftWall.setAsBox(5, Gdx.graphics.getHeight());
		rightWall.setAsBox(5, Gdx.graphics.getHeight());
		ceiling.setAsBox(Gdx.graphics.getWidth(), 5);
		pieces.add(floor);
		pieces.add(leftWall);
		pieces.add(rightWall);
		pieces.add(ceiling);
		readLevel(levelName);
		for(BodyWrap b: pieces) {
			b.create();
		}
		for(Player p: players) {
			p.getBodyDef().linearDamping = Constants.PLAYER_DAMPING;
			p.setAsCircle(2);
			p.setPieces(pieces);
			p.create();
		}
	}
	private void readLevel(String levelName) {
		/* Order of .lvl data
		 * x , y , bodyType, [boundingbox], bodyshape, [radius], [width , height], *

		 */
		FileHandle f = Gdx.files.internal("level2.lvl");
		String levelData = f.readString();
		String[] lines = levelData.split("\n");
		for(String s: lines) {
			String[] parts = s.split(" ");
			parts[parts.length-1] = parts[parts.length-1].trim();
			int x = Integer.valueOf(parts[0]);
			int y = Integer.valueOf(parts[1]);
			int width = 0, height = 0, radius = 0, boxX = 0, 
					boxY = 0, boxWidth = 0, boxHeight = 0, velX = 0,
					velY = 0;
			boolean hasBoundBox = false;
			Shape.Type shape = null;
			BodyType type = null;
			if(parts[2].equalsIgnoreCase("Static")) {
				type = BodyType.StaticBody;

				if(parts[3].equalsIgnoreCase("Box")) {
					shape = Shape.Type.Polygon;
					width = Integer.valueOf(parts[4]);
					height = Integer.valueOf(parts[5]);
				}
				else if(parts[3].equalsIgnoreCase("Circle")) {
					shape = Shape.Type.Circle;
					radius = Integer.valueOf(parts[4]);
				} else {
					Gdx.app.log("## Error ##", "Unable to load Shape.Type - " + s);
				}
			} else {
				if(parts[2].equalsIgnoreCase("Kinetic")) {
					type = BodyType.KinematicBody;
				} else if(parts[2].equalsIgnoreCase("Dynamic")) {
					type = BodyType.DynamicBody;
				} else {
					Gdx.app.log("## Error ##", "Unable to load BodyType - " + s);
				}
				if(!isInteger(parts[3])) {
					if(parts[3].equalsIgnoreCase("Box")) {
						shape = Shape.Type.Polygon;
						width = Integer.valueOf(parts[4]);
						height = Integer.valueOf(parts[5]);
					} else if(parts[3].equalsIgnoreCase("Circle")) {
						shape = Shape.Type.Circle;
						radius = Integer.valueOf(parts[4]);
					} else {
						Gdx.app.log("## Error ##", "Unable to load Shape.Type - " + s);
					}
				} else {
					boxX = Integer.valueOf(parts[3]);
					boxY = Integer.valueOf(parts[4]);
					boxWidth = Integer.valueOf(parts[5]);
					boxHeight = Integer.valueOf(parts[6]);
					hasBoundBox = true;
					if(parts[7].equalsIgnoreCase("Box")) {
						shape = Shape.Type.Polygon;
						width = Integer.valueOf(parts[8]);
						height = Integer.valueOf(parts[9]);
						if(parts.length > 10) {
							velX = Integer.valueOf(parts[10]);
							velY = Integer.valueOf(parts[11]);
						}
					} else if(parts[7].equalsIgnoreCase("Circle")) {
						shape = Shape.Type.Circle;
						radius = Integer.valueOf(parts[8]);
						if(parts.length > 9) {
							velX = Integer.valueOf(parts[9]);
							velY = Integer.valueOf(parts[10]);
						}
					} else {
						Gdx.app.log("## Error ##", "Unable to load Shape.Type - " + s);
					}
				}

			}if(type == null) {
				type = BodyType.StaticBody;
			}
			pieces.add(new BodyWrap(world, type, x ,y));
			switch(shape) {
			case Chain:
				break;
			case Circle:
				pieces.get(pieces.size()-1).setAsCircle(radius);
				break;
			case Edge:
				break;
			case Polygon:
				pieces.get(pieces.size()-1).setAsBox(width, height);
				break;
			default:
				Gdx.app.log("## Error ##", "Count not create Body - " + s);
				break;
			}
			switch(type) {
			case DynamicBody:
				pieces.get(pieces.size()-1).setDynamic();
				break;
			case KinematicBody:
				pieces.get(pieces.size()-1).setKinetic();
				pieces.get(pieces.size()-1).setLinV(new Vector2(velX, velY));
				break;
			case StaticBody:
				break;
			default:
				Gdx.app.log("## Error ##", "Could not create Body - " + s);
				break;
			}
			if(hasBoundBox) {
				pieces.get(pieces.size()-1).setBoundingBox(boxX, boxY, boxWidth, boxHeight);
			}
		}
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Player getMainPlayer() {
		return mainPlayer;
	}
	public void setMainPlayer(int index) {
		this.index = Math.abs(index);
		mainPlayer = players.get(this.index % Constants.PLAYER_AMT);
	}
	public ArrayList<BodyWrap> getPieces() {
		return pieces;
	}
	private boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}

