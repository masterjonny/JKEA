package jkea.simulation;

import java.util.ArrayList;

class AESDevice {

	private final AESCipher cipher = new AESCipher();
	private short[] key;
	private short[] plain;
	private final ArrayList<HammingTemplate> templates;

	AESDevice(final ArrayList<HammingTemplate> templates) {
		this.templates = templates;
		newKey();
		newPlain();
	}

	final short[] getKey() {
		return key;
	}

	final short[] getPlain() {
		return plain;
	}

	final double[] getTrace() {
		final short[] output = cipher.predict(plain, key);
		final double[] leakage = new double[0x10];
		for (int i = 0; i < 0x10; i++)
			leakage[i] = templates.get(i).leak(output[i]);
		return leakage;
	}

	void newKey() {
		key = cipher.newBlock();
	}

	void newPlain() {
		plain = cipher.newBlock();
	}

}
