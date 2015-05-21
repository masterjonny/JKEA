package jkea.solvers.glowacz.data;

import jkea.util.data.Complex;

public class Histogram {

	private int[] data;
	private double binSize;
	
	public double getBinSize() {
		return binSize;
	}

	public Histogram(int nBins, double binSize) {
		data = new int[nBins];
		for(int i = 0; i < nBins; data[i++] = 0);
		this.binSize = binSize;
	}
	
	public Histogram(Complex[] data) {
		this.data = new int[data.length];
		for(int i = 0; i < data.length; i++) {
			this.data[i] = (int) data[i].re(); 
		}
		this.binSize = 0;
	}
	
	public void add(double val) {	
		int binID = (int)(val / binSize);
		if(binID == data.length)
			binID--;
		data[binID]++;
	}
	
	public Complex[] toComplex() {
		Complex res[] = new Complex[data.length];
		for(int i = 0; i < data.length; i++) 
			res[i] = new Complex(data[i], 0);
		return res;
	}
	
	public void add(int val, int binID) {
		data[binID] = val;
	}
	
	public int getBin(int id) {
		return data[id];
	}
	
	public int getNBins() {
		return data.length;
	}
	
}
