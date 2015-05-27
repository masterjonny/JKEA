package jkea.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Read text from a character-input stream, ignoring lines starting with the #
 * character. Lines are only ignored when using the {@link #readLine} method.
 */
public class CommentedLineReader extends BufferedReader {

	/**
	 * Constructs a buffered reader that ignores lines starting with the #
	 * character.
	 *
	 * @param in
	 *            a reader
	 */
	public CommentedLineReader(Reader in) {
		super(in);
	}

	/**
	 * Constructs a buffered reader that ignores lines starting with the #
	 * character with the specified input buffer size.
	 *
	 * @param in
	 *            a reader
	 * @param sz
	 *            the input buffer size
	 */
	public CommentedLineReader(Reader in, int sz) {
		super(in, sz);
	}

	@Override
	public String readLine() throws IOException {
		String line = super.readLine();

		// skip over comments
		while ((line != null) && line.startsWith("#")) {
			line = super.readLine();
		}

		return line;
	}

}
