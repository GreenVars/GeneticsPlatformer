package com.me.genetics;

import java.util.Random;

import com.badlogic.gdx.Input.Keys;
import com.me.helpers.Constants;

public class Gene {
	private Random r = new Random();
	public float runTime;
	private int[] possible_controls = { 
			Keys.A,
			Keys.S,
			Keys.W,
			Keys.D,
	};
	public int key;
	public Gene() {
		key = randomKey();
		runTime = randomTime();
	}
	public Gene(int c) {
		key = c;
		runTime = randomTime();
	}
	public Gene(float runTime) {
		key = randomKey();
		this.runTime = runTime;
	}
	public Gene(int c, float runTime) {
		key = c;
		this.runTime = runTime;
	}
	public int randomKey() {
		return possible_controls[r.nextInt(possible_controls.length)];
	}
	public float randomTime() {
		float f = r.nextFloat();
		return (f*Constants.SECONDS_MAX) + Constants.SECONDS_MIN;
	}
	public void mutate() {
		float f = r.nextFloat();
		if(f < Constants.TIME_MUTATE_CHANCE) mutateTime();
		else mutateKey();
	}
	public void mutateTime() {
		runTime = randomTime();
	}
	public void mutateKey() {
		key = randomKey();
	}
	public void mutateBoth() {
		mutateTime();
		mutateKey();
	}
	public void crossover(Gene g) {
		float f = r.nextFloat();
		if(f < Constants.KEY_MUTATE_CHANCE) {
			int c = g.key;
			g.key = this.key;
			this.key = g.key;
		} else {
			float rT = g.runTime * r.nextFloat();
			g.runTime = this.runTime * r.nextFloat();
			this.runTime = rT;
		}
	}
	@Override
	public String toString() {
		return "|" + key + "| for " + runTime;
	}
}
