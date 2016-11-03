package com.me.helpers;

import com.me.genetics.Gene;
import com.me.objects.Player;

public class GeneRunner {
	private Gene gene;
	private Long benchmark;
	private InputHandler handler;
	public boolean done = false;
	public GeneRunner(Gene g, InputHandler hand) {
		gene = g;
		this.handler = hand;
	}
	public void doGene(Player player) {
		if(benchmark == null) {
			if(Constants.PRINT_GENE_STREAM)
				System.out.println(gene);
			benchmark = System.nanoTime();

		}
		if(System.nanoTime() - benchmark < gene.runTime*Math.pow(10, 9)) {
			handler.PlayerKeyDown(gene.key, player);
			handler.PlayerKeyUp(gene.key, player); // TODO INVESTIGATE
		} else {
			done  = true;
			handler.PlayerKeyUp(gene.key, player); 
		}
	}
}
