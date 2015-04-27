package jkea.solvers.bristol.data;

import java.util.ArrayList;

public class WorkBlock {

	private int accept;
	private int capacity;
	private int fail;
	private int length;
	private int wMin;
	private ArrayList<Integer> paths;
	
	public WorkBlock() {
	}

	public int getAccept() {
		return accept;
	}

	public ArrayList<Integer> getPaths() {
		return paths;
	}

	public void setPaths(ArrayList<Integer> paths) {
		this.paths = paths;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getFail() {
		return fail;
	}

	public int getLength() {
		return length;
	}

	public int getwMin() {
		return wMin;
	}

	public void setAccept(int accept) {
		this.accept = accept;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setwMin(int wMin) {
		this.wMin = wMin;
	}

}
