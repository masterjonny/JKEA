package jkea.solvers.bristol.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class KnapsackTest {

	Knapsack testClass;

	@Before
	public void setUp() throws Exception {
		final int[][] data = new int[3][3];
		final int[] data1 = { 3, 1, 6 };
		final int[] data2 = { 4, 2, 3 };
		final int[] data3 = { 1, 7, 8 };
		data[0] = data1;
		data[1] = data2;
		data[2] = data3;
		testClass = new Knapsack(data);

	}

	@Test
	public void testCapacity() {
		final int[] depths = { 0, 1, 0 };
		assertEquals(5, testClass.calculateCapacity(depths));
	}

	@Test
	public void testGetChunks() {
		assertEquals(3, testClass.getChunks());
	}

	@Test
	public void testGetMinCapacity() {
		assertEquals(4, testClass.calcualteMinCapacity());
	}

	@Test
	public void testGetRawScores() {
		final int[][] sorted = testClass.getRawScores();
		assertEquals(3, sorted[0][0]);
		assertEquals(4, sorted[1][0]);
		assertEquals(1, sorted[2][0]);
	}

	@Test
	public void testGetRows() {
		assertEquals(3, testClass.getRows());
	}

	@Test
	public void testGetSortedScores() {
		final int[][] sorted = testClass.getSortedScores();
		assertEquals(1, sorted[0][0]);
		assertEquals(2, sorted[1][0]);
		assertEquals(1, sorted[2][0]);
	}

}
