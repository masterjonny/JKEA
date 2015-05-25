package jkea.core;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.Template;

/**
 * Interface for implementing different distinguishers. Distinguishers are used
 * to process the output of a {@link Simulator}, where the leak data is
 * processed such that the distinguishing vectors for the attack are generated,
 * or updated.
 *
 */
public interface Distinguisher {

	/**
	 * Given a known plain text, and a trace of leak data, update the provided
	 * distinguishing vectors.
	 *
	 * @param prior
	 *            the distinguishing vectors to be updated
	 * @param plain
	 *            known plain text sequence
	 * @param trace
	 *            trace data based on the known plain text
	 * @param cipher
	 *            the cipher on which the trace data being distinguished is
	 *            encrypted against
	 * @param templates
	 *            the set of templates used to create this trace
	 * @return an updated set of distinguishing vectors
	 *
	 */
	double[][] updatePrior(double[][] prior, final short[] plain,
			final double[] trace, Cipher cipher, ArrayList<Template> templates);

}
