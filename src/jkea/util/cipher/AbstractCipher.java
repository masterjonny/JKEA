package jkea.util.cipher;

import java.util.Random;

import jkea.core.PRNG;

/**
 * Default implementations of, and supporting data structures for the
 * {@link Cipher} class.
 *
 */
public abstract class AbstractCipher implements Cipher {

	/**
	 * The internal source of randomness.
	 */
	protected final Random numberStream;

	/**
	 * The length of each block in this cipher.
	 */
	protected int blockLength;

	/**
	 * The maximum value a key chunk in a block is permitted to be.
	 */
	protected int keyRange;

	/**
	 * Initialise the basic data structure of the cipher class.
	 *
	 * @param blockLength
	 *            the length of each block in this cipher
	 * @param keyRange
	 *            the maximum value each chunk in a key block is permitted to be
	 */
	public AbstractCipher(int blockLength, int keyRange) {
		numberStream = PRNG.getRandom();
		this.blockLength = blockLength;
		this.keyRange = keyRange;
	}

	@Override
	public short[] newBlock() {
		short block[] = new short[blockLength];
		for (int i = 0; i < blockLength; i++) {
			block[i] = (short) numberStream.nextInt(keyRange);
		}
		return block;
	}
}
