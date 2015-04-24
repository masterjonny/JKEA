package jkea.simulation;

import java.util.ArrayList;

class TemplateAttack {

	final private AESCipher cipher = new AESCipher();
	final private ArrayList<HammingTemplate> templates;

	TemplateAttack(final ArrayList<HammingTemplate> templates) {
		this.templates = templates;
	}

	final double[][] updatePrior(double[][] prior, final short[] plain,
			final double[] leak) {
		final double[][] newPrior = prior;
		for (int i = 0; i < 0x10; i++)
			for (int j = 0; j < 0x100; j++)
				newPrior[i][j] += Math.log(templates.get(i).leakDensity(
						leak[i], cipher.predict(plain[i], (short) j)));
		return newPrior;
	}

}