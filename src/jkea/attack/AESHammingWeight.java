package jkea.attack;

import java.util.ArrayList;

import jkea.core.Distinguisher;
import jkea.util.cipher.AES;
import jkea.util.data.HammingTemplate;
import jkea.util.data.LeakPoint;

public class AESHammingWeight extends AbstractAttack {

	private class AESDevice extends AbstractDevice {

		AESDevice(final ArrayList<LeakPoint> templates) {
			super(templates, new AES());
		}
	}

	AESDevice device;
	ArrayList<LeakPoint> templates;

	AESHammingWeight(int nTraces, int variance, Distinguisher distinguisher) {
		super(16, 256, distinguisher);
		for (int i = 0; i < 0x10; i++)
			templates.add(new HammingTemplate(variance));

		device = new AESDevice(templates);
		key = device.getKey();
		runAttack(nTraces);
	}

	private void runAttack(int nTraces) {
		final AES c = new AES();
		for (int i = 0; i < 0x10; i++)
			for (int j = 0; j < 0x100; j++)
				vectors[i][j] = 1. / 0x100;

		for (long i = 0; i < nTraces; i++) {
			device.newPlain();
			final short[] plain = device.getPlain();
			final double[] trace = device.getTrace();
			vectors = distinguisher.updatePrior(vectors, plain, trace, c,
					templates);
		}
	}

}
