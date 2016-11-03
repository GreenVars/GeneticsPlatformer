package com.me.genetics;


public class GeneticsTester {

	public static void main(String[] args) {
		Sequence s = new Sequence();
		Sequence q = new Sequence();
		System.out.println(s);
		System.out.println(q);
		s.breed(q);
		System.out.println(s);
		System.out.println(q);
		
		Population p = new Population();
		p.nextGeneration();
		System.out.println(p.organisms.size());
	}

}
