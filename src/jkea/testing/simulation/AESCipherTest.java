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

	@Test
	public void predictTest() {
		short[] solutions = { 99, 212, 35, 179, 67, 255, 144, 121, 232, 248,
				45, 212, 99, 241, 0, 255, 67, 115, 78, 62, 65, 17, 35, 241, 99,
				182, 177, 132, 73, 94, 45, 102, 232, 179, 0, 182, 99, 21, 5,
				193, 105, 236, 172, 200, 67, 255, 177, 21, 99, 212, 137, 31,
				145, 151, 11, 255, 67, 132, 5, 212, 99, 233, 181, 213, 222, 23,
				144, 115, 73, 193, 137, 233, 99, 18, 88, 245, 80, 121, 78, 94,
				105, 31, 181, 18, 99, 133, 47, 252, 232, 62, 45, 236, 145, 213,
				88, 133, 99, 165, 35, 248, 65, 102, 172, 151, 222, 245, 47,
				165, 99, 175, 45, 17, 232, 200, 11, 23, 80, 252, 35, 175, 99 };

		int count = 0;
		for (short i = 0; i < 256; i += 25) {
			for (short j = 0; j < 256; j += 25) {
				assertEquals(solutions[count], testClass.predict(i, j));
				count++;
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictBoundsUpper() {
		testClass.predict((short) 1, (short) 256);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictBoundsUpper2() {
		testClass.predict((short) 256, (short) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictBoundsLower() {
		testClass.predict((short) -1, (short) 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictBoundsLower2() {
		testClass.predict((short) 1, (short) -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictSizeMismatch() {
		short[] block1 = new short[15];
		short[] block2 = new short[14];
		testClass.predict(block1, block2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictSizeMismatchTwo() {
		short[] block1 = new short[15];
		short[] block2 = new short[14];
		testClass.predict(block2, block1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void predictSizeTooBig() {
		short[] block1 = new short[16];
		short[] block2 = new short[14];
		testClass.predict(block2, block1);
	}

}
