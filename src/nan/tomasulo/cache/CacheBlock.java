package nan.tomasulo.cache;

public class CacheBlock {
	private int size; // size in words

	private CacheEntry[] entries;

	public CacheBlock(int size, CacheEntry[] entries) {
		this.size = size;
		this.entries = entries;
	}

	public CacheBlock(int size) {
		this.size = size;

		this.entries = new CacheEntry[size];
		initializeEntries();
	}

	/**
	 * 
	 * @param offset
	 *            the offset of the cache entry in this block
	 * @return the entry specified by the offset passed, null if the offset is
	 *         out of range
	 */
	public CacheEntry getEntry(short offset) {
		if (offset <= this.entries.length - 1) {
			return this.entries[offset];
		}
		return null;
	}

	private void initializeEntries() {
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new CacheEntry();
		}
	}

	// Getters and Setters

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public CacheEntry[] getEntries() {
		return entries;
	}

	public void setEntries(CacheEntry[] entries) {
		this.entries = entries;
	}

}
