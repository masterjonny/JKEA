package jkea.distinguisher;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.Template;

/**
 * Implementation of a distinguisher that performs a template attack.
 *
 */
public class TemplateAttack extends AbstractDistinguisher {

	/**
	 * Construct a new template attack.
	 */
	public TemplateAttack() {
		super();
	}

	@Override
	public double[][] updatePrior(double[][] prior, final short[] plain,
			final double[] leak, Cipher cipher, ArrayList<Template> templates) {
		final double[][] newPrior = prior;
		for (int i = 0; i < 0x10; i++) {
			for (int j = 0; j < 0x100; j++) {
				newPrior[i][j] += Math.log(templates.get(i).leakDensity(
						leak[i], cipher.predict(plain[i], (short) j)));
			}
		}
		return newPrior;
	}
}
