package jkea.util.data.kea;

import java.math.BigDecimal;
import java.util.ArrayList;

public class WorkBlock {

	private int accept;
	private int capacity;
	private int fail;
	private int length;
	private ArrayList<BigDecimal> paths;
	private int wMin;
	private BigDecimal pathCount;

	public WorkBlock() {
	}

	public int getAccept() {
		return accept;
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

	public BigDecimal getPathCount() {
		return pathCount;
	}

	public ArrayList<BigDecimal> getPaths() {
		return paths;
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

	public void setPathCount(BigDecimal pathCount) {
		this.pathCount = pathCount;
	}

	public void setPaths(ArrayList<BigDecimal> paths) {
		this.paths = paths;
	}

	public void setwMin(int wMin) {
		this.wMin = wMin;
	}

}
