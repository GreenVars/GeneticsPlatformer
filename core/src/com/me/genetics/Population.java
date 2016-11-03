package com.me.genetics;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.me.helpers.Constants;
import com.me.objects.Player;
import com.me.world.PlatformWorld;

public class Population {
	private Random r = new Random();
	private PlatformWorld physicsWorld;
	public ArrayList<Sequence> organisms;

	public Population(ArrayList<Player> players) {
		organisms = new ArrayList<Sequence>();
		for(int i = 0; i < Constants.POPULATION_START_LENGTH; i++) {
			organisms.add(new Sequence(players.get(i)));
		}
	}
	public Population(PlatformWorld physicsWorld) {
		this.physicsWorld = physicsWorld;
		ArrayList<Player> players = physicsWorld.getPlayers();
		organisms = new ArrayList<Sequence>();
		for(int i = 0; i < Constants.POPULATION_START_LENGTH; i++) {
			organisms.add(new Sequence(players.get(i)));
		}
	}
	public Population() {
		organisms = new ArrayList<Sequence>();
		for(int i = 0; i < Constants.POPULATION_START_LENGTH; i++) {
			organisms.add(new Sequence());
		}
	}
	public Population(ArrayList<Sequence> sequence, ArrayList<Player> players) {
		this.organisms = sequence;
	}
	public void randomBreedByPairs() {
		ArrayList<Sequence> newPopulation = new ArrayList<Sequence>();
		for(int i = 0; i < organisms.size()/2; i++) {
			Sequence mom = randomOrganism(true);
			Sequence dad = randomOrganism(true);
			dad.breed(mom);
			newPopulation.add(mom);
			newPopulation.add(dad);
		}
		organisms = newPopulation;
	}
	public void randomBreed() {
		for(int i = 0; i < organisms.size()/2; i++) {
			organisms.get(i).breed(randomOrganism(false));
		}
	}
	public Sequence randomOrganism(boolean delete) {
		int index = r.nextInt(organisms.size());
		Sequence s = organisms.get(index);
		if(delete)
			organisms.remove(index);
		return s;
	}
	public Sequence breedStrongest() {
		Sequence dad = strongestOrganism();
		Sequence mom = strongestOrganism();
		dad.breed(mom);
		return dad;
	}
	public Sequence strongestOrganism() {
		Sequence strongest = organisms.get(0);
		for(Sequence s: organisms) {
			if(aptitude(s) > aptitude(strongest)) {
				strongest = s;
			}
		}
		Player strongPlayer = strongest.player;
		strongPlayer.getWorld().destroyBody(strongPlayer.getBody()); // Basically suicide
		organisms.remove(strongest);
		return strongest;
	}
	public float aptitude(Sequence s) {
		Vector2 pos = s.player.getPosition();
		return pos.x + pos.y*5; // sum of cordinates
	}
	public void nextGeneration() {
		ArrayList<Sequence> nextGen = new ArrayList<Sequence>();
		int threshold = organisms.size();
		for(int i = 0; i < threshold/2; i++) {
			nextGen.add(breedStrongest());
			
			Player toAdd = new Player(physicsWorld.getPhysicsWorld(), 20, 20);
			toAdd.getBodyDef().linearDamping = Constants.PLAYER_DAMPING;
			toAdd.setAsCircle(2);
			toAdd.setPieces(physicsWorld.getLevel().getPieces());
			toAdd.create();
			nextGen.add(new Sequence(toAdd)); // fill population
		}
		organisms = nextGen;
		for(Sequence o: organisms) {
			o.player.getPosition().set(20, 20);
		}
	}
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for(Sequence organ: organisms) {
			players.add(organ.player);
		}
		return players;
	}
}
