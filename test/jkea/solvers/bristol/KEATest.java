package jkea.solvers.bristol;

import static org.junit.Assert.*;

import java.util.ArrayList;

import jkea.solvers.bristol.data.WorkBlock;

import org.junit.Before;
import org.junit.Test;

public class KEATest {

	KEA testClass;

	@Before
	public void setUp() throws Exception {
		int[][] scores = new int[3][3];
		int[] scores1 = { 7, 1, 9 };
		int[] scores2 = { 4, 3, 3 };
		int[] scores3 = { 6, 1, 2 };
		scores[0] = scores1;
		scores[1] = scores2;
		scores[2] = scores3;
		short[] key = { 0, 1, 2 };
		testClass = new KEA(scores, key);
	}

	@Test
	public void testPathCount() {
		WorkBlock w = new WorkBlock();
		w.setFail(63);
		w.setAccept(64);
		w.setCapacity(7);
		w.setLength(65);
		testClass.pathCountLoop(w);
		ArrayList<Integer> paths = w.getPaths();
		int[] solutions = { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
				1, 0, 0, 0 };
		for (int i = 0; i < 63; i++) {
			System.out.println(i);
			assertEquals(solutions[i], (int)paths.get(i));
		}
	}

	@Test
	public void zeroChildTest() {
		WorkBlock w = new WorkBlock();
		w.setFail(63);
		w.setCapacity(7);
		int[] solutions = { 1, 2, 63, 4, 5, 63, 7, 8, 63, 10, 11, 63, 13, 14,
				63, 16, 17, 63, 19, 20, 63, 22, 23, 63, 25, 26, 63, 28, 29, 63,
				31, 32, 63, 34, 35, 63, 37, 38, 63, 40, 41, 63, 43, 44, 63, 46,
				47, 63, 49, 50, 63, 52, 53, 63, 55, 56, 63, 58, 59, 63, 61, 62,
				63 };
		for (int i = 0; i < 63; i++) {
			assertEquals(solutions[i], testClass.zeroChild(w, i));
		}
	}

	@Test
	public void oneChildTest() {
		WorkBlock w = new WorkBlock();
		w.setFail(63);
		w.setAccept(64);
		w.setCapacity(7);
		int[] solutions = { 63, 24, 63, 63, 27, 63, 63, 30, 63, 63, 33, 63, 63,
				36, 63, 63, 39, 63, 63, 63, 63, 54, 51, 51, 57, 54, 54, 60, 57,
				57, 63, 60, 60, 63, 63, 63, 63, 63, 63, 63, 63, 63, 64, 64, 64,
				63, 64, 64, 63, 64, 64, 63, 64, 64, 63, 64, 64, 63, 64, 63, 63,
				63, 63 };
		for (int i = 0; i < 63; i++) {
			assertEquals(solutions[i], testClass.oneChild(w, i));
		}
	}
}
