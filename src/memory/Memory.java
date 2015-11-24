package memory;

import java.util.Arrays;

public class Memory {
	private short[] memory_data;
	private int memorySize;
	private int blockSize; // Block Size = # of words inside the block
	private int numOfBlocks;

	public Memory() {
		this.memorySize = 64 * 1024 / 16;
		this.blockSize = memorySize;
		this.numOfBlocks = 1;
		this.memory_data = new short[memorySize];
	}

	public Memory(int size, int blockSize) {
		this.memorySize = size;
		this.blockSize = blockSize;
		this.numOfBlocks = size / blockSize + (size % blockSize == 0 ? 0 : 1);
		this.memory_data = new short[memorySize];
	}

	public void setData(int address, short data) {
		memory_data[address] = data;
	}

	public short getData(int address) {
		return memory_data[address];
	}

	public int getBlockSize() {
		return blockSize;
	}

	/**
	 * address is the address of the word inside the memory 0 based
	 * 
	 * @param address
	 * @return the block that contains the given wordAddress
	 */
	public short[] getBlock(int wordAddress) {
		return Arrays.copyOfRange(memory_data, wordAddress / numOfBlocks,
				Math.min(wordAddress / numOfBlocks + blockSize, memorySize));
	}
}
