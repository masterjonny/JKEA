package jkea.simulation;

import java.util.ArrayList;

class AESDevice {

	final ArrayList<HammingTemplate> templates;
	final AESCipher cipher = new AESCipher();

	short[] key;
	short[] plain;

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

	void newKey() {
		key = cipher.newBlock();
	}

	void newPlain() {
		plain = cipher.newBlock();
	}
	
	final double[] getTrace() {
		short[] output = cipher.predict(plain, key);
		double[] leakage = new double[0x10];
		for(int i = 0; i < 0x10; i++) {
			leakage[i] = templates.get(i).leak(output[i]);
		}
		return leakage;
	}

}
