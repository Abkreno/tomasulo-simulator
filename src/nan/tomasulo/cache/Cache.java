package nan.tomasulo.cache;

import java.util.LinkedList;

import nan.tomasulo.utils.Constants.WritePolicy;

public class Cache {

	private CacheBlock[] blocks;

	private int size; // total size of the cache in words
	private int blockSize; // size of blocks in words
	private int numOfBlocks;
	private int associativity;
	private int numOfSets; // depends on associativity

	private int hits;
	private int misses;

	private int accessDelay; // number of cycles to access the cache

	private WritePolicy writePolicy;

	public Cache(int size, int blockSize, int associativity, int accessDelay,
			WritePolicy writePolicy) {
		this.size = size;
		this.blockSize = blockSize;
		this.numOfBlocks = size / blockSize + (size % blockSize == 0 ? 0 : 1);
		this.associativity = associativity;
		this.setWritePolicy(writePolicy);
		this.numOfSets = numOfBlocks / associativity;
		this.hits = 0;
		this.misses = 0;
		this.accessDelay = accessDelay;
		this.blocks = new CacheBlock[size / blockSize];
		initializeBlocks();
	}

	private void initializeBlocks() {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new CacheBlock(blockSize);
		}
	}

	// Getters and Setters

	public CacheBlock[] getBlocks() {
		return blocks;
	}

	public void setBlocks(CacheBlock[] blocks) {
		this.blocks = blocks;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getAssociativity() {
		return associativity;
	}

	public void setAssociativity(int associativity) {
		this.associativity = associativity;
	}

	public WritePolicy getWritePolicy() {
		return writePolicy;
	}

	public void setWritePolicy(WritePolicy writePolicy) {
		this.writePolicy = writePolicy;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	public void setNumOfBlocks(int numOfBlocks) {
		this.numOfBlocks = numOfBlocks;
	}

	public int getNumOfSets() {
		return numOfSets;
	}

	public void setNumOfSets(int numOfSets) {
		this.numOfSets = numOfSets;
	}

	public int getAccessDelay() {
		return accessDelay;
	}

	public void setAccessDelay(int accessDelay) {
		this.accessDelay = accessDelay;
	}

	public double getHitRatio() {
		if (hits + misses == 0)
			return 1;
		double hits = this.hits;
		double misses = this.misses;
		return hits / (hits + misses);
	}

	public double getMissRatio() {
		if (hits + misses == 0)
			return 0;
		double hits = this.hits;
		double misses = this.misses;
		return misses / (hits + misses);
	}

	public short getIndex(short address) {
		address /= blockSize; // remove the offset bits
		return (short) (address % numOfSets);
	}

	public short getTag(short address) {
		address /= blockSize;
		return (short) (address / numOfSets);
	}

	public short getOffset(short address) {
		return (short) (address % blockSize);
	}

	public LinkedList<CacheBlock> getSet(short setNum) {
		LinkedList<CacheBlock> set = new LinkedList<>();
		int numOfBlocks = 0;
		int blockIndex = setNum * associativity;
		while (numOfBlocks++ < associativity) {
			set.add(blocks[blockIndex]);
			blockIndex++;
		}
		return set;
	}

	public String toString() {
		return String
				.format("Misses = %d / Hits = %d ", getMisses(), getHits());
	}

	public CacheBlock getCacheBlock(short address) {
		short setNum = getIndex(address);
		short tag = getTag(address);
		LinkedList<CacheBlock> set = getSet(setNum);
		CacheBlock LRU = null;
		for (CacheBlock block : set) {
			if (block.getTag() == tag && block.isValid()) {
				return block;
			} else if (LRU == null) {
				LRU = block;
			}
		}
		return LRU;
	}

}
