package jkea.simulation;

import java.util.ArrayList;

class TemplateAttack {

	final private ArrayList<HammingTemplate> templates;
	final private AESCipher cipher = new AESCipher();

	TemplateAttack(final ArrayList<HammingTemplate> templates) {
		if (templates.size() != 0x10) {
			throw new IllegalArgumentException(
					"Hamming template size must be 16");
		}
		this.templates = templates;
	}

	final double[][] updatePrior(double[][] prior, final short[] plain,
			final double[] leak) {
		if (prior.length != 0x10) {
			throw new IllegalArgumentException("Invalid prior size");
		}
		for (int i = 0; i < 0x10; i++) {
			if (prior[i].length != 0x100) {
				throw new IllegalArgumentException("Invalid prior inner size");
			}
		}
		if (plain.length != 0x10) {
			throw new IllegalArgumentException("Invalid plain size");
		}
		if (leak.length != plain.length) {
			throw new IllegalArgumentException("Plain/leak size mismatch");
		}

		double[][] newPrior = prior;
		for (int i = 0; i < 0x10; i++) {
			for (int j = 0; j < 0x100; j++) {
				newPrior[i][j] += Math.log(templates.get(i).leakDensity(
						leak[i], cipher.predict((short) plain[i], (short) j)));
			}
		}
		return newPrior;
	}

}
