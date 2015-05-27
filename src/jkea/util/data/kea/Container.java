package jkea.util.data.kea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jkea.util.data.Pair;

/**
 * Class that allows the storing of a distinguishing vector as a list of key
 * chunks, with associated scores.
 */
public class Container {

	/**
	 * Comparator class to allow the sorting of this container by key chunk
	 * value.
	 */
	private class SortKeyComparator implements Comparator<Pair<Short, Integer>> {

		@Override
		public int compare(Pair<Short, Integer> arg0, Pair<Short, Integer> arg1) {
			return arg0.getValue0().compareTo(arg1.getValue0());
		}

	}

	/**
	 * Comparator class to allow the sorting of this container by key chunk
	 * scores.
	 */
	private class SortScoreComparator implements
			Comparator<Pair<Short, Integer>> {

		@Override
		public int compare(Pair<Short, Integer> arg0, Pair<Short, Integer> arg1) {
			return arg0.getValue1().compareTo(arg1.getValue1());
		}

	}

	/**
	 * The list of pairs of key chunks and associated scores.
	 */
	private ArrayList<Pair<Short, Integer>> data;

	/**
	 * Create a new container based on a list of scores.
	 *
	 * @param scores
	 *            a list of scores which is sorted by key chunk. ie Cell 0
	 *            contains the score for key chunk {@code 0x00} and cell 256
	 *            contains the score for key chunk {@code 0x100}
	 */
	public Container(int[] scores) {
		data = new ArrayList<Pair<Short, Integer>>(scores.length);
		for (Short i = 0; i < scores.length; i++) {
			data.add(new Pair<Short, Integer>(i, scores[i]));
		}
	}

	/**
	 * Return a lit of the scores this container stores.
	 *
	 * @return a list of the scores being stored in this container
	 */
	public int[] getAllScores() {
		int[] scores = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			scores[i] = data.get(i).getValue1();
		}
		return scores;
	}

	/**
	 * Get the key chunk at a specified index.
	 *
	 * @param index
	 *            the index to retrieve the key chunk from
	 * @return the key chunk at the specified index
	 */
	public short getKeyChunk(int index) {
		return data.get(index).getValue0();
	}

	/**
	 * Get the score value at a specified index.
	 *
	 * @param index
	 *            the index to retrieve the score from
	 * @return the score at the specified index
	 */
	int getScore(int index) {
		return data.get(index).getValue1();
	}

	/**
	 * Get the length of this container.
	 *
	 * @return the length of this container
	 */
	int getSize() {
		return data.size();
	}

	/**
	 * Sort this container by key chunk values.
	 */
	void sortKeyChunk() {
		Collections.sort(data, new SortKeyComparator());
	}

	/**
	 * Sort this container by score values.
	 */
	void sortScore() {
		Collections.sort(data, new SortScoreComparator());
	}
}
