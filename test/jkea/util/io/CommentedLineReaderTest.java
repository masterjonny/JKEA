package jkea.util.io;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the {@link CommentedLineReader} class.
 */
public class CommentedLineReaderTest {

	/**
	 * Tests if the {@code CommentedLineReader} correctly handles empty files.
	 *
	 * @throws IOException
	 *             should not occur
	 */
	@Test
	public void testEmpty1() throws IOException {
		final CommentedLineReader reader = new CommentedLineReader(
				new StringReader(""));

		Assert.assertNull(reader.readLine());

		reader.close();
	}

	/**
	 * Tests if the {@code CommentedLineReader} correctly handles an input file
	 * containing all commented lines.
	 *
	 * @throws IOException
	 *             should not occur
	 */
	@Test
	public void testEmpty2() throws IOException {
		final CommentedLineReader reader = new CommentedLineReader(
				new StringReader("#comment line\n# comment line"));

		Assert.assertNull(reader.readLine());

		reader.close();
	}

	/**
	 * Tests if the {@code CommentedLineReader} correctly handles a normal
	 * input.
	 *
	 * @throws IOException
	 *             should not occur
	 */
	@Test
	public void testExample() throws IOException {
		final CommentedLineReader reader = new CommentedLineReader(
				new StringReader(
						"#comment line\nnon-comment line\n# comment line"));

		Assert.assertEquals("non-comment line", reader.readLine());
		Assert.assertNull(reader.readLine());

		reader.close();
	}

}