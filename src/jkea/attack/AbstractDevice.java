package jkea.attack;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.LeakPoint;

public abstract class AbstractDevice {

	private final Cipher cipher;
	private short[] key;
	private short[] plain;
	private final ArrayList<LeakPoint> templates;

	AbstractDevice(final ArrayList<LeakPoint> templates, Cipher cipher) {
		this.cipher = cipher;
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
