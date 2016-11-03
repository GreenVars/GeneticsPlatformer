package com.me.genetics;

import java.util.ArrayList;
import java.util.Random;

import com.me.helpers.Constants;
import com.me.objects.Player;

public class Sequence {
	private Random r = new Random();
	public Player player;
	public ArrayList<Gene> dna;
	public Sequence() {
		dna = new ArrayList<Gene>();
		for(int i = 0; i < Constants.SEQUENCE_START_LENGTH; i++) {
			dna.add(new Gene());
		}
	}
	public Sequence(ArrayList<Gene> DNA) {
		this.dna = DNA;
	}
	public Sequence(Player player2) {
		this.player = player2;
		dna = new ArrayList<Gene>();
		for(int i = 0; i < Constants.SEQUENCE_START_LENGTH; i++) {
			dna.add(new Gene());
		}
	}
	public void setPlayer(Player p) {
		player = p;
	}
	public void selfMutate() {
		for(Gene g: dna) {
			if(r.nextFloat() < Constants.SEQUENCE_MUTATE_CHANCE) {
				g.mutate();
			}
		}
	}
	public void breed(Sequence other) {
		for(int i = 0; i < dna.size(); i++) {
			dna.get(i).crossover(other.dna.get(i));
		}
	}
	public void addGenes(int amount) {
		for(int i = 0; i < amount; i++) {
			dna.add(new Gene());
		}
	}
	@Override
	public String toString() {
		String output = "";
		for(Gene g: dna) {
			output += g.toString() + "\n";
		}
		return output;
	}
} 
