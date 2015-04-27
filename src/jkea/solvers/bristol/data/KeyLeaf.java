package jkea.solvers.bristol.data;

import java.util.ArrayList;

public class KeyLeaf {

	private short keyChunk;
	private ArrayList<KeyLeaf> keyList;
	private boolean hasList;
	
	public KeyLeaf(short keyChunk) {
		this.keyChunk = keyChunk;
		hasList = false;
	}
	
	public KeyLeaf(short keyChunk, ArrayList<KeyLeaf> linkedList) {
		this.keyChunk = keyChunk;
		this.keyList = linkedList;
		hasList = true;
	}
	
	public boolean hasList() {
		return hasList;
	}

	public short getKeyChunk() {
		return keyChunk;
	}

	public ArrayList<KeyLeaf> getKeyList() {
		return keyList;
	}
		
}
