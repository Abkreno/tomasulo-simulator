package nan.tomasulo.memory;

import java.util.Arrays;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.utils.Constants;

public final class Memory {
	private static Object[] memoryData;
	private static int memorySize;
	private static int blockSize; // Block Size = # of words inside the block
	private static int numOfBlocks;
	private static int programSize;
	private static short programBeginning; // The starting address of the
											// program

	public static void init() {
		Memory.memorySize = 64 * 1024 / 16;
		Memory.programSize = -1;
		Memory.blockSize = memorySize;
		Memory.numOfBlocks = Memory.memorySize / blockSize
				+ (Memory.memorySize % blockSize == 0 ? 0 : 1);
		Memory.memoryData = new Object[memorySize];
		Memory.programBeginning = -1;
	}

	public static void init(int blockSize, int programSize, int programBeginning) {
		Memory.memorySize = 64 * 1024 / 16;
		Memory.programSize = programSize;
		Memory.blockSize = blockSize;
		Memory.numOfBlocks = Memory.memorySize / blockSize
				+ (Memory.memorySize % blockSize == 0 ? 0 : 1);
		Memory.memoryData = new Object[memorySize];
		Memory.programBeginning = (short) programBeginning;
	}

	private Memory() {
		init();
	}

	/**
	 * Reads the block that contains the provided word address from data space
	 * 
	 * @param address
	 *            the address of the target word
	 * @return the block that contains the given wordAddress
	 * @throws InvalidReadException
	 *             if the address is in program space or out of memory space
	 * 
	 */
	public static Object[] readDataBlock(int wordAddress)
			throws InvalidReadException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidReadException(
					"Target address is out of memory space");
		}

		return Arrays.copyOfRange(memoryData, (wordAddress / blockSize)
				* blockSize, Math.min((wordAddress / blockSize) * blockSize
				+ blockSize, memorySize));
	}

	/**
	 * Write data to specific address in the data space
	 * 
	 * @param wordAddress
	 * @param data
	 * @throws InvalidWriteException
	 *             if the address is in program space or out of memory space
	 */
	public static void writeDataEntry(int wordAddress, Object data)
			throws InvalidWriteException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidWriteException(
					"Target address is out of memory space");
		} else if (data instanceof Short && wordAddress >= programBeginning
				&& wordAddress < programBeginning + programSize) {
			throw new InvalidWriteException(
					String.format(
							"Attempt to write data to instruction space for address %d",
							wordAddress));
		} else if (data instanceof String && wordAddress < programBeginning
				|| wordAddress > programBeginning + programSize) {
			throw new InvalidWriteException(
					String.format(
							"Attempt to write instruction to data space for address %d",
							wordAddress));
		}

		memoryData[wordAddress] = data;
	}

	// getters and setters

	public static Object[] getMemoryData() {
		return memoryData;
	}

	public static void setMemoryData(Object[] memoryData) {
		Memory.memoryData = memoryData;
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
