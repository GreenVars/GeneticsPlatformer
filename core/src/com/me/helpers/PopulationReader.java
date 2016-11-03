package com.me.helpers;

import java.util.ArrayList;

import com.me.genetics.Population;
import com.me.genetics.Sequence;
import com.me.world.PlatformWorld;

public class PopulationReader {
	private Population population;
	private ArrayList<SequenceReader> readers;
	private InputHandler handler;
	private PlatformWorld world;
	public PopulationReader(Population p, InputHandler handler, PlatformWorld world) {
		population = p;
		this.handler = handler;
		this.world = world;
		readers = new ArrayList<SequenceReader>();
		for(int i = 0; i < Constants.PLAYER_AMT; i++) {
			readers.add(new SequenceReader(population.organisms.get(i), 
							 handler, 
							 world.getPlayers().get(i)));
		}
	}
	public void run() {
		for(SequenceReader reader: readers) {
			reader.run();
		}
		if(allDone() && population.organisms.size() > 2) {
			System.out.println("Next Gen");
			population.nextGeneration();
			world.addPlayers(population.getPlayers());
			for(int i = 0; i < population.getPlayers().size(); i++) {
				readers.add(new SequenceReader(population.organisms.get(i),
							handler,
							population.getPlayers().get(i)));
			}
		}
	}
	private boolean allDone() {
		for(SequenceReader reader: readers) {
			if(!reader.completed) {
				return false;
			}
		}
		return true;
	}

}
