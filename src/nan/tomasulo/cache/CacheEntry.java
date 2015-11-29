package nan.tomasulo.cache;

public class CacheEntry {

	private Object data;

	public CacheEntry(Object data) {
		this.data = data;
	}

	public CacheEntry() {
		this.data = null;
	}

	@Override
	public String toString() {
		return data.toString();
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
