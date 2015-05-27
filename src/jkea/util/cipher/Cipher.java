package jkea.util.cipher;

import jkea.core.Distinguisher;
import jkea.simulator.AbstractDevice;

/**
 * Interface for implementing different ciphers. Ciphers are used in both
 * {@link Distinguisher} and {@link AbstractDevice} to perform the encryption of
 * a provided plain text against a given key.
 *
 */
public interface Cipher {

	/**
	 * For a given plain text and key, perform the encryption.
	 *
	 * @param plain
	 *            the plain text to encrypt
	 * @param key
	 *            the key to encrypt against
	 * @return the encrypted plain text
	 */
	short encrypt(short plain, short key);

	/**
	 * For a given plain text and key sequence , perform the encryption.
	 *
	 * @param plain
	 *            the plain text to encrypt
	 * @param key
	 *            the key to encrypt against
	 * @return the encrypted plain text
	 */
	public short[] encrypt(short[] plain, short[] key);

	/**
	 * Generate a new random block permissible under the parameters of this
	 * cipher for use as a plain text or a key
	 *
	 * @return the newly generated random block
	 */
	public short[] newBlock();

}
