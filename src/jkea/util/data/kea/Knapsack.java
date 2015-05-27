package jkea.util.data.kea;

import java.util.ArrayList;

public class Knapsack {

	private int chunks;
	private ArrayList<Container> data;
	private int rows;

	public Knapsack(int[][] scores) {
		chunks = scores.length;
		rows = scores[0].length;
		data = new ArrayList<Container>(chunks);
		for (int i = 0; i < chunks; i++)
			data.add(new Container(scores[i]));
	}

	public int calcualteMinCapacity() {
		int[] depths = new int[chunks];
		for (int i = 0; i < chunks; i++)
			depths[i] = 0;
		return calculateCapacity(depths);
	}

	public int calculateCapacity(int[] depths) {
		int total = 0;
		for (int i = 0; i < chunks; i++) {
			data.get(i).sortScore();
			total += data.get(i).getScore(depths[i]);
			data.get(i).sortKeyChunk();
		}
		return total;
	}
	
	public int calculateCapacityWithoutSort (int[] depths) {
		int total = 0;
		for (int i = 0; i < chunks; i++) {
			total += data.get(i).getScore(depths[i]);;
		}
		return total;
	}
	
	public int calculateCapacityWithoutSort (short[] depths) {
		int[] intDepths = new int[depths.length];
		for(int i = 0; i < depths.length; i++) 
			intDepths[i] = (int)depths[i];
		
		return this.calculateCapacityWithoutSort(intDepths);
	}
	
	public void sortScore() {
		for (int i = 0; i < chunks; i++)
			data.get(i).sortScore();
	}

	public void sortKeyChunk() {
		for (int i = 0; i < chunks; i++)
			data.get(i).sortKeyChunk();
	}
	
	public int getChunks() {
		return chunks;
	}

	int[][] getRawScores() {
		int[][] scores = new int[chunks][rows];
		for (int i = 0; i < chunks; i++)
			scores[i] = data.get(i).getAllScores();
		return scores;
	}

	public int getRows() {
		return rows;
	}

	public int[][] getSortedScores() {
		int[][] scores = new int[chunks][rows];
		for (int i = 0; i < chunks; i++) {
			data.get(i).sortScore();
			scores[i] = data.get(i).getAllScores();
			data.get(i).sortKeyChunk();
		}
		return scores;
	}

}
