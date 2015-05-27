package jkea.distinguisher;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.template.Template;

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
	public double[][] updatePrior(double[][] prior, short[] plain,
			double[] leak, Cipher cipher, ArrayList<Template> templates) {
		double[][] newPrior = prior;
		for (int i = 0; i < prior.length; i++) {
			for (int j = 0; j < prior[0].length; j++) {
				newPrior[i][j] += Math.log(templates.get(i).leakDensity(
						leak[i], cipher.encrypt(plain[i], (short) j)));
			}
		}
		return newPrior;
	}
}
