package nan.tomasulo.cache;

public class CacheEntry {
	private short tag; // Memory is addressable with 16-bit, no more than 16 bit
	private boolean valid;
	private boolean dirty;

	private short data;

	public CacheEntry(short tag, short data, boolean valid, boolean dirty) {
		this.tag = tag;
		this.data = data;
		this.valid = valid;
		this.dirty = dirty;
	}

	public CacheEntry(short tag, short data) {
		this.tag = tag;
		this.data = data;
		this.valid = true;
		this.dirty = false;
	}

	public CacheEntry() {
		this.tag = -1;
		this.data = 0;
		this.valid = false;
		this.dirty = false;
	}

	@Override
	public String toString() {
		return String.format("TAG : %d\tdata : %d\nvalid : %b\tdirty : %b", this.tag, this.data, this.valid,
				this.dirty);
	}
	
	// Getters and Setters

	public short getTag() {
		return tag;
	}

	public void setTag(short tag) {
		this.tag = tag;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}

}
