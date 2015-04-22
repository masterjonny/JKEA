package jkea.simulation;

import java.lang.Math;

public class HammingTemplate {

	private double stdDeviation;
	private double cNorm;
	
	public HammingTemplate(double variance) {
		adjust_variance(variance);
	}
	
	private void adjust_variance(double variance) {
		stdDeviation = Math.sqrt(variance);
		cNorm = 1. / Math.sqrt(2 * Math.PI * variance);
	}
	
	public short hammingWeight(short transition) {
		int x = transition;
		x = (x & 0x55) + ((x >> 1) & 0x55);
		x = (x & 0x33) + ((x >> 2) & 0x33);
		x = (x & 0xf) + (x >> 4);
		return (short)x;
	}
	
}
