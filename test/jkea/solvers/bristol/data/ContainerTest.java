package jkea.solvers.bristol.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ContainerTest {

	Container testClass;

	@Test
	public void getSizeTest() {
		assertEquals(3, testClass.getSize());
	}

	@Before
	public void setUp() throws Exception {
		final int[] probs = { 3, 2, 1 };
		testClass = new Container(probs);
	}

	@Test
	public void sortKeyChunkTest() {
		testClass.sortKeyChunk();
		assertEquals(1, testClass.getScore(2));
	}

	@Test
	public void sortScoreTest() {
		testClass.sortScore();
		assertEquals(2, testClass.getKeyChunk(0));
	}

}