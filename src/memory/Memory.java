package memory;

import java.util.Arrays;

public class Memory {
	private short[] memory_data;
	private int memorySize;
	private int blockSize; // Block Size = # of words inside the block

	public Memory() {
		this.memorySize = 64 * 1024 / 16;
		this.blockSize = memorySize;
		this.memory_data = new short[memorySize];
	}

	public Memory(int size, int blockSize) {
		this.memorySize = size;
		this.blockSize = blockSize;
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

	public short[] getBlock(int offset) {
		return Arrays.copyOfRange(memory_data, offset,
				Math.min(offset + blockSize, memorySize));
	}
}
