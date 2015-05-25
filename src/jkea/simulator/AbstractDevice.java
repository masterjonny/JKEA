package jkea.simulator;

import java.util.ArrayList;

import jkea.util.cipher.Cipher;
import jkea.util.data.Template;

/**
 * Abstract class providing basic functionality of device simulators, for
 * specific devices to expand upon.
 *
 */
public abstract class AbstractDevice {

	/**
	 * The cipher which this simulated device uses.
	 */
	private final Cipher cipher;

	/**
	 * The known secret key which this device is using.
	 */
	private short[] key;

	/**
	 * The known plain text which this device is simulating against.
	 */
	private short[] plain;

	/**
	 * The templates constructed to perform the side channel attack.
	 */
	private final ArrayList<Template> templates;

	/**
	 * Construct a new device for simulating attacks.
	 *
	 * @param templates
	 *            the templates used to perform the attack
	 * @param cipher
	 *            the cipher which the simulate device is using
	 */
	AbstractDevice(final ArrayList<Template> templates, Cipher cipher) {
		this.cipher = cipher;
		this.templates = templates;
		newKey();
		newPlain();
	}

	/**
	 * Get the known secret key of the device.
	 *
	 * @return the known secret key.
	 */
	final short[] getKey() {
		return key;
	}

	/**
	 * Get the known plain text of the device.
	 *
	 * @return the known plain text of the simulated device
	 */
	final short[] getPlain() {
		return plain;
	}

	/**
	 * Get a leakage trace from the device.
	 *
	 * @return a trace of leakage simulated from the device
	 */
	final double[] getTrace() {
		final short[] output = cipher.predict(plain, key);
		final double[] leakage = new double[templates.size()];
		for (int i = 0; i < templates.size(); i++) {
			leakage[i] = templates.get(i).leak(output[i]);
		}
		return leakage;
	}

	/**
	 * Generate a new, random, known secret key for the device.
	 */
	void newKey() {
		key = cipher.newBlock();
	}

	/**
	 * Generate a new, random, known plain text for the device.
	 */
	void newPlain() {
		plain = cipher.newBlock();
	}

}
