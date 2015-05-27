package jkea.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import jkea.TestUtils;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

	private File getTempFolder() throws IOException {
		final File file = File.createTempFile("test", null);
		file.delete();

		final File directory = new File(file.getParent(), file.getName());
		directory.deleteOnExit();

		return directory;
	}

	@Test
	public void testDelete() throws IOException {
		final File file = TestUtils.createTempFile();

		FileUtils.delete(file);

		Assert.assertFalse(file.exists());
	}

	@Test
	public void testDeleteNonexistentFile() throws IOException {
		final File file = TestUtils.createTempFile();
		file.delete();

		FileUtils.delete(file);

		Assert.assertFalse(file.exists());
	}

	@Test
	public void testMkdir() throws IOException {
		FileUtils.mkdir(getTempFolder());
	}

	@Test
	public void testMkdirAlreadyExists() throws IOException {
		final File directory = getTempFolder();

		FileUtils.mkdir(directory);
		FileUtils.mkdir(directory);
	}

	@Test(expected = IOException.class)
	public void testMkdirButFileExistsWithName() throws IOException {
		final File file = TestUtils.createTempFile();

		FileUtils.mkdir(file);
	}

	@Test
	public void testMove() throws IOException {
		final File from = TestUtils.createTempFile("foobar");
		final File to = TestUtils.createTempFile();

		FileUtils.move(from, to);

		Assert.assertFalse(from.exists());
		Assert.assertTrue(to.exists());
		Assert.assertEquals(1, TestUtils.lineCount(to));
		TestUtils.assertLinePattern(to, "foobar");
	}

	@Test(expected = FileNotFoundException.class)
	public void testMoveNonexistentFile() throws IOException {
		final File from = TestUtils.createTempFile("foobar");
		final File to = TestUtils.createTempFile();

		from.delete();

		FileUtils.move(from, to);
	}

	@Test
	public void testMoveSame() throws IOException {
		final File file = TestUtils.createTempFile("foobar");

		FileUtils.move(file, file);

		Assert.assertTrue(file.exists());
		Assert.assertEquals(1, TestUtils.lineCount(file));
		TestUtils.assertLinePattern(file, "foobar");
	}

}
