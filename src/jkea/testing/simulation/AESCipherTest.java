package jkea.testing.simulation;

import static org.junit.Assert.*;
import jkea.simulation.AESCipher;

import org.junit.Before;
import org.junit.Test;

public class AESCipherTest {

	AESCipher testClass;

	@Before
	public void setUp() throws Exception {
		testClass = new AESCipher();
	}

	@Test
	public void newBlockTest() {
		short[] result = testClass.newBlock();
		assertEquals(0x10, result.length);
		short minVal = 32767;
		short maxVal = -32767;

		for (int k = 0; k < 10000; k++) {
			for (int i = 0; i < 0x10; i++) {
				if (result[i] < minVal) {
					minVal = result[i];
				}
				if (result[i] > maxVal) {
					maxVal = result[i];
				}
			}
		}
		assertTrue(0 <= minVal && minVal <= 256);
		assertTrue(0 <= maxVal && maxVal <= 256);

	}

}
