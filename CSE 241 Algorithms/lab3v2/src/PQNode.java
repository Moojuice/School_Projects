package lab3;

public class PQNode<T> {
	private Handle handle;
	private int key;
	private T value;
	public PQNode(int key, T value) {
		this.key = key;
		this.value = value;
	}
	public Handle getHandle() {
		return handle;
	}
	public void setHandle(Handle handle) {
		this.handle = handle;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
}
