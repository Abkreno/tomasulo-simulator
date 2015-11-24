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
	 * Returns a specific entry in the cache block, checks the validity and tag
	 * before returning it
	 * 
	 * @param tag
	 *            tag of the entry obtained from the memory address
	 * @param offset
	 *            the offset of the entry in the current block
	 * @return the cache entry if it is valid and the tag is the same as the
	 *         passed tag, null otherwise
	 */
	public CacheEntry getEntry(short tag, short offset) {
		if (offset <= this.entries.length - 1) {
			if (this.entries[offset] != null && this.entries[offset].getTag() == tag
					&& this.entries[offset].isValid()) {
				return this.entries[offset];
			}
		} else {
			System.err.println("The offset provided is greater than the offset of the current block");
		}

		return null;
	}

	/**
	 * returning a specific entry from that block without check on the validity
	 * or the tag of it
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
