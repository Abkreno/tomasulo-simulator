package nan.tomasulo.memory;

import java.util.Arrays;

import nan.tomasulo.exceptions.InvalidReadException;

public final class Memory {
	private static short[] memoryData;
	private static String[] programData;
	private static int memorySize;
	private static int blockSize; // Block Size = # of words inside the block
	private static int numOfBlocks;
	private static int programSize;
	private static short programBeginning; // The starting address of the

	public static void init(int blockSize, int programSize, short programBeginning) {
		Memory.memorySize = 64 * 1024 / 16;
		Memory.programSize = programSize;
		Memory.blockSize = blockSize;
		Memory.numOfBlocks = Memory.memorySize / blockSize + (Memory.memorySize % blockSize == 0 ? 0 : 1);
		Memory.memoryData = new short[memorySize];
		Memory.programBeginning = programBeginning;
	}

	private Memory() {

	}

	/**
	 * Reads the block that contains the provided word address from data space
	 * 
	 * @param address
	 *            the address of the target word
	 * @return the block that contains the given wordAddress
	 * @throws InvalidReadException
	 *             if the address is in program space or greater than the memory
	 *             space
	 * 
	 */
	public static short[] readDataBlock(int wordAddress) throws InvalidReadException {
		if (wordAddress > memorySize) {
			throw new InvalidReadException("Target address is greater than the memory capacity");
		} else if (wordAddress >= programBeginning && wordAddress <= programBeginning + programSize) {
			throw new InvalidReadException(
					String.format("Attempt to read data from instruction space for address %d", wordAddress));
		}

		return Arrays.copyOfRange(memoryData, wordAddress / numOfBlocks,
				Math.min(wordAddress / numOfBlocks + blockSize, memorySize));
	}

	/**
	 * Write data to specific address in the memory
	 * 
	 * @param wordAddress
	 * @param data
	 * @throws InvalidReadException
	 *             if the address is in program space or greater than the memory
	 *             space
	 */
	public static void writeDataEntry(int wordAddress, short data) throws InvalidReadException {
		if (wordAddress > memorySize) {
			throw new InvalidReadException("Target address is greater than the memory capacity");
		} else if (wordAddress >= programBeginning && wordAddress <= programBeginning + programSize) {
			throw new InvalidReadException(
					String.format("Attempt to write data toinstruction space for address %d", wordAddress));
		}

		memoryData[wordAddress] = data;
	}

	// getters and setters

	public static short[] getMemoryData() {
		return memoryData;
	}

	public static void setMemoryData(short[] memoryData) {
		Memory.memoryData = memoryData;
	}

	public static String[] getProgramData() {
		return programData;
	}

	public static void setProgramData(String[] programData) {
		Memory.programData = programData;
	}

	public static int getMemorySize() {
		return memorySize;
	}

	public static void setMemorySize(int memorySize) {
		Memory.memorySize = memorySize;
	}

	public static int getBlockSize() {
		return blockSize;
	}

	public static void setBlockSize(int blockSize) {
		Memory.blockSize = blockSize;
	}

	public static int getNumOfBlocks() {
		return numOfBlocks;
	}

	public static void setNumOfBlocks(int numOfBlocks) {
		Memory.numOfBlocks = numOfBlocks;
	}

	public static int getProgramSize() {
		return programSize;
	}

	public static void setProgramSize(int programSize) {
		Memory.programSize = programSize;
	}

	public static short getProgramBeginning() {
		return programBeginning;
	}

	public static void setProgramBeginning(short programBeginning) {
		Memory.programBeginning = programBeginning;
	}

}
