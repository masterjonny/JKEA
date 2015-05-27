package jkea.util.data.kea;

import java.util.ArrayList;

/**
 * A node within the key tree used by {@link KEA} to enumerate the keys. Each
 * node contains a list to its children, as well as a boolean denoting if these
 * children have been initialised or not.
 */
public class KeyLeaf {

	/**
	 * The key chunk this node is storing.
	 */
	private short keyChunk;

	/**
	 * The list of children that come after this node.
	 */
	private ArrayList<KeyLeaf> keyList;

	/**
	 * {@code true} if this node has children and {@code false} if it does not.
	 */
	private boolean hasList;

	/**
	 * Create a leaf node for the key tree, ie a key node with no children.
	 *
	 * @param keyChunk
	 *            the key chunk this node is to store
	 */
	public KeyLeaf(short keyChunk) {
		this.keyChunk = keyChunk;
		hasList = false;
	}

	/**
	 * Create a node of the tree.
	 *
	 * @param keyChunk
	 *            the key chunk this node is to store
	 * @param linkedList
	 *            the list of children this node contains
	 */
	public KeyLeaf(short keyChunk, ArrayList<KeyLeaf> linkedList) {
		this.keyChunk = keyChunk;
		keyList = linkedList;
		hasList = true;
	}

	/**
	 * Get the key chunk of this node.
	 * 
	 * @return the key chunk this node is storing
	 */
	public short getKeyChunk() {
		return keyChunk;
	}

	/**
	 * Get the children of this node.
	 *
	 * @return the list of children this node has
	 */
	public ArrayList<KeyLeaf> getKeyList() {
		return keyList;
	}

	/**
	 * Checks if this node has children. {@code true} is returned if it does and
	 * {@code false} if not.
	 * 
	 * @return a boolean denoting if this not has children
	 */
	public boolean hasList() {
		return hasList;
	}

}