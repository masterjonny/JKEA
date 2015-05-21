package jkea.util.cipher;

public interface Cipher {

	public short[] newBlock();

	short predict(short plain, short key);

	public short[] predict(final short[] plain, final short[] key);

}
