package jkea.solvers.bristol.data;

import java.util.ArrayList;

public class Knapsack {

	private final int chunks;
	private final ArrayList<Container> data;
	private final int rows;

	public Knapsack(int[][] scores) {
		if (scores.length == 0)
			throw new IllegalArgumentException("Scores matrix is empty!");
		for (final int[] score : scores)
			if (score.length != scores[0].length)
				throw new IllegalArgumentException("Score matrix size mismatch");
		chunks = scores.length;
		rows = scores[0].length;
		data = new ArrayList<Container>(chunks);
		for (int i = 0; i < chunks; i++)
			data.add(new Container(scores[i]));
	}

	public int calcualteMinCapacity() {
		final int[] depths = new int[chunks];
		for (int i = 0; i < chunks; i++)
			depths[i] = 0;
		return calculateCapacity(depths);
	}

	public int calculateCapacity(int[] depths) {
		if (depths.length != chunks)
			throw new IllegalArgumentException("Incorrect number of depths");
		int total = 0;
		for (int i = 0; i < chunks; i++) {
			data.get(i).sortScore();
			total += data.get(i).getScore(depths[i]);
			data.get(i).sortKeyChunk();
		}
		return total;
	}
	
	public int calculateCapacityWithoutSort (int[] depths) {
		if (depths.length != chunks)
			throw new IllegalArgumentException("Incorrect number of depths");
		int total = 0;
		for (int i = 0; i < chunks; i++) {
			total += data.get(i).getScore(depths[i]);;
		}
		return total;
	}
	
	public void sortScore() {
		for (int i = 0; i < chunks; i++)
			data.get(i).sortScore();
	}

	public void sortKeyChunk() {
		for (int i = 0; i < chunks; i++)
			data.get(i).sortScore();
	}
	
	public int getChunks() {
		return chunks;
	}

	int[][] getRawScores() {
		final int[][] scores = new int[chunks][rows];
		for (int i = 0; i < chunks; i++)
			scores[i] = data.get(i).getAllScores();
		return scores;
	}

	public int getRows() {
		return rows;
	}

	public int[][] getSortedScores() {
		final int[][] scores = new int[chunks][rows];
		for (int i = 0; i < chunks; i++) {
			data.get(i).sortScore();
			scores[i] = data.get(i).getAllScores();
			data.get(i).sortKeyChunk();
		}
		return scores;
	}

}
