package jkea.solvers.bristol.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jkea.util.data.Pair;

class Container {

	private class SortKeyComparator implements Comparator<Pair<Short, Double>> {

		@Override
		public int compare(Pair<Short, Double> arg0, Pair<Short, Double> arg1) {
			return arg0.getValue0().compareTo(arg1.getValue0());
		}

	}

	private class SortScoreComparator implements
	Comparator<Pair<Short, Double>> {

		@Override
		public int compare(Pair<Short, Double> arg0, Pair<Short, Double> arg1) {
			return arg0.getValue1().compareTo(arg1.getValue1());
		}

	}

	private final ArrayList<Pair<Short, Double>> data;

	Container(double[] scores) {
		data = new ArrayList<Pair<Short, Double>>(scores.length);
		for (Short i = 0; i < scores.length; i++)
			data.add(new Pair<Short, Double>(i, scores[i]));
	}

	double[] getAllScores() {
		final double[] scores = new double[data.size()];
		for (int i = 0; i < data.size(); i++)
			scores[i] = data.get(i).getValue1();
		return scores;
	}

	short getKeyChunk(int index) {
		return data.get(index).getValue0();
	}

	double getScore(int index) {
		return data.get(index).getValue1();
	}

	int getSize() {
		return data.size();
	}

	void sortKeyChunk() {
		data.sort(new SortKeyComparator());
	}

	void sortScore() {
		Collections.sort(data, new SortScoreComparator());
	}
}
