package nan.tomasulo.cache;

import java.util.LinkedList;

import nan.tomasulo.utils.Constants.CacheType;
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

	private WritePolicy writePolicy;

	public Cache(int size, int blockSize, int associativity,
			WritePolicy writePolicy) {
		this.size = size;
		this.blockSize = blockSize;
		this.numOfBlocks = size / blockSize + size % blockSize == 0 ? 0 : 1;
		this.associativity = associativity;
		this.setWritePolicy(writePolicy);
		this.numOfSets = numOfBlocks / associativity;
		this.hits = 0;
		this.misses = 0;

		this.blocks = new CacheBlock[size / blockSize];
		initializeBlocks();
	}

	/*
	 * cacheInfo is [ size , blockSize , associativity ]
	 */
	public Cache(int[] cacheInfo, WritePolicy writePolicy) {
		this(cacheInfo[0], cacheInfo[1], cacheInfo[2], writePolicy);
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

	public CacheBlock getCacheBlock(short address) {
		short setNum = getIndex(address);
		short tag = getTag(address);
		LinkedList<CacheBlock> set = getSet(setNum);
		for (CacheBlock block : set) {
			if (block.getTag() == tag && block.isValid()) {
				return block;
			}
		}
		return null; // the targeted cache block was not found
	}

	public CacheEntry getCacheEntry(short address) {
		CacheBlock block = getCacheBlock(address);
		if (block == null)
			return null; // the block was not found search in a lower cache
		short offset = getOffset(address);
		return block.getEntry(offset);
	}

}
