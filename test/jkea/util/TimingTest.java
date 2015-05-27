package jkea.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import jkea.TestUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link Timing} class.
 */
public class TimingTest {

	private File saveMagnitudes() throws IOException {
		final File file = TestUtils.createTempFile();
		PrintStream ps = null;

		try {
			ps = new PrintStream(new FileOutputStream(file));

			Timing.printMagnitudes(ps);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

		return file;
	}

	private File saveStatistics() throws IOException {
		final File file = TestUtils.createTempFile();
		PrintStream ps = null;

		try {
			ps = new PrintStream(new FileOutputStream(file));

			Timing.printStatistics(ps);
		} finally {
			if (ps != null) {
				ps.close();
			}
		}

		return file;
	}

	@Test
	public void testClear() throws IOException {
		Timing.clear();
		Assert.assertEquals(0, TestUtils.lineCount(saveStatistics()));
		Assert.assertEquals(0, TestUtils.lineCount(saveMagnitudes()));

		Timing.startTimer("timer1");
		Timing.stopTimer("timer1");
		Timing.startTimer("timer2");
		Timing.stopTimer("timer2");

		Assert.assertEquals(2, TestUtils.lineCount(saveStatistics()));
		Assert.assertEquals(2, TestUtils.lineCount(saveMagnitudes()));
		Assert.assertNotNull(Timing.getStatistics("timer1"));
		Assert.assertNotNull(Timing.getStatistics("timer2"));

		Timing.clear();
		Assert.assertEquals(0, TestUtils.lineCount(saveStatistics()));
		Assert.assertEquals(0, TestUtils.lineCount(saveMagnitudes()));
		Assert.assertNull(Timing.getStatistics("timer1"));
		Assert.assertNull(Timing.getStatistics("timer2"));
	}

	@Test
	public void testClearWithActiveTimers() {
		Timing.clear();

		Timing.startTimer("timer1");
		Timing.clear();
		Timing.stopTimer("timer1");

		Assert.assertEquals(1, Timing.getStatistics("timer1").getN());
	}

	@Test(expected = Exception.class)
	public void testDuplicateTimer() {
		Timing.clear();

		Timing.startTimer("testDuplicateTimer1");
		Timing.startTimer("testDuplicateTimer2");
		Timing.startTimer("testDuplicateTimer1");
	}

	@Test(expected = Exception.class)
	public void testNonexistentTimer() {
		Timing.clear();

		Timing.stopTimer("testNonExistentTimer");
	}

	@Test
	public void testNormalUse() {
		Timing.clear();

		Timing.startTimer("timer1");
		Timing.startTimer("timer2");
		Timing.stopTimer("timer2");
		Timing.startTimer("timer2");
		Timing.stopTimer("timer1");
		Timing.stopTimer("timer2");

		Assert.assertEquals(1, Timing.getStatistics("timer1").getN());
		Assert.assertEquals(2, Timing.getStatistics("timer2").getN());
	}

}
