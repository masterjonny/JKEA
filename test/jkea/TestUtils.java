package jkea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

import jkea.util.io.CommentedLineReader;

import org.junit.Assert;

/**
 * Utility methods for testing.
 */
public class TestUtils {

	/**
	 * Asserts that every line on the file matches the specified regular
	 * expression pattern. This method automatically ignores commented lines.
	 *
	 * @param file
	 *            the file
	 * @param regex
	 *            the regular expression pattern
	 * @throws IOException
	 *             if an I/O error occurred
	 */
	public static void assertLinePattern(File file, String regex)
			throws IOException {
		CommentedLineReader reader = null;
		final Pattern pattern = Pattern.compile(regex);

		try {
			reader = new CommentedLineReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				if (!pattern.matcher(line).matches()) {
					Assert.fail("line does not match pattern: " + line);
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * Creates a temporary file for testing purposes. The temporary file will be
	 * deleted on exit.
	 *
	 * @return the temporary file
	 * @throws IOException
	 *             if an I/O error occurred
	 */
	public static File createTempFile() throws IOException {
		final File file = File.createTempFile("test", null);
		file.deleteOnExit();
		return file;
	}

	/**
	 * Creates a temporary file containing the specified data. The temporary
	 * file will be deleted on exit.
	 *
	 * @param data
	 *            the contents of the temporary file
	 * @return the temporary file
	 * @throws IOException
	 *             if an I/O error occurred
	 */
	public static File createTempFile(String data) throws IOException {
		final File file = createTempFile();

		Writer writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(data);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

		return file;
	}

	/**
	 * Returns the number of lines in the specified file.
	 *
	 * @param file
	 *            the file
	 * @return the number of lines in the specified file
	 * @throws IOException
	 *             if an I/O error occurred
	 */
	public static int lineCount(File file) throws IOException {
		BufferedReader reader = null;
		int count = 0;

		try {
			reader = new BufferedReader(new FileReader(file));

			while (reader.readLine() != null) {
				count++;
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return count;
	}

}
