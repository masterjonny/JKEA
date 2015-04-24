package jkea.util.data;

import junit.framework.TestCase;

public class TupleTest extends TestCase {

	public void testMain() throws Exception {
		final Pair<String, Object> pair = Pair.with("a", null);

		final Object o = null;
		assertTrue(pair.contains("a"));
		assertTrue(pair.contains(null));
		assertTrue(pair.containsAll(o));
		assertTrue(pair.containsAll(null, "a"));
		assertTrue(!pair.containsAll(null, "b"));

		final Pair<String, Integer> pair1 = Pair.with("hello",
				Integer.valueOf(23));
		final Pair<String, Integer> pair2 = new Pair<String, Integer>("hello",
				Integer.valueOf(23));
		System.out.println(pair1);
		System.out.println(pair2);
	}

}
