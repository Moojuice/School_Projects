package lab3;

public class Handle {
	/*
	 * extract node, set handle to -1
	 */
	private int index;
	public Handle(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String toString() {
		return ""+index;
		
	}
}
