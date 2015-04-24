package jkea.simulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AESDeviceTest {

	AESDevice testClass;

	@Before
	public void setUp() throws Exception {
		final ArrayList<HammingTemplate> testTemplates = new ArrayList<HammingTemplate>(
				16);
		for (int i = 0; i < 16; i++)
			testTemplates.add(new HammingTemplate(1));
		testClass = new AESDevice(testTemplates);
	}

	@Test
	public void testGetTrace() {
		final double[] trace = testClass.getTrace();
		assertEquals(0x10, trace.length);

	}

	@Test
	public void testNewKey() {
		testClass.newKey();
		final short[] oldKey = testClass.getKey();
		testClass.newKey();
		final short[] newKey = testClass.getKey();

		final int shortestLength = Math.min(oldKey.length, newKey.length);

		int matchingCount = 0;
		for (int i = 0; i < shortestLength; i++)
			if (oldKey[i] == newKey[i])
				matchingCount++;
		if ((matchingCount == shortestLength)
				&& (oldKey.length == newKey.length))
			fail("Key unchanged");
	}

	@Test
	public void testNewPlain() {
		testClass.newPlain();
		final short[] oldPlain = testClass.getPlain();
		testClass.newPlain();
		final short[] newPlain = testClass.getPlain();

		final int shortestLength = Math.min(oldPlain.length, newPlain.length);

		int matchingCount = 0;
		for (int i = 0; i < shortestLength; i++)
			if (oldPlain[i] == newPlain[i])
				matchingCount++;
		if ((matchingCount == shortestLength)
				&& (oldPlain.length == newPlain.length))
			fail("Plain unchanged");
	}
}
