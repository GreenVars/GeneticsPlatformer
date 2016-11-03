package com.me.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.me.genetics.Gene;
import com.me.genetics.Sequence;
import com.me.objects.Player;

public class SequenceReader {
	private InputHandler handler;
	private Sequence dna;
	private ArrayList<GeneRunner> runners;
	private Player player;
	public boolean completed;
	public SequenceReader(Sequence s, InputHandler i, Player p) {
		dna = s;
		//System.out.println(dna);
		handler = i;
		player = p;
		runners = new ArrayList<GeneRunner>();
		for(Gene g: dna.dna) {
			runners.add(new GeneRunner(g, i));
		}
	}
	public void run() {
		if(runners.size() > 0) {
			completed = false;
			runners.get(0).doGene(player);
			if(runners.get(0).done) {
				player.normalizeX();
				runners.remove(0);
			}
		} else {
			completed = true;
		}
	}
}