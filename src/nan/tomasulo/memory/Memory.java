package nan.tomasulo.memory;

import java.util.Arrays;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;

public final class Memory {
	private static Short[] memoryData;
	private static String[] programData;
	private static int memorySize;
	private static int blockSize; // Block Size = # of words inside the block
	private static int numOfBlocks;
	private static int programSize;
	private static int programBeginning; // The starting address of the program

	public static void init() {
		Memory.memorySize = 64 * 1024 / 16;
		Memory.programSize = -1;
		Memory.blockSize = memorySize;
		Memory.numOfBlocks = Memory.memorySize / blockSize + (Memory.memorySize % blockSize == 0 ? 0 : 1);
		Memory.memoryData = new Short[memorySize];
		Memory.programBeginning = -1;
		Memory.programData = null;
	}

	public static void init(int blockSize, int programSize, int programBeginning) {
		Memory.memorySize = 64 * 1024 / 16;
		Memory.programSize = programSize;
		Memory.blockSize = blockSize;
		Memory.numOfBlocks = Memory.memorySize / blockSize + (Memory.memorySize % blockSize == 0 ? 0 : 1);
		Memory.memoryData = new Short[memorySize];
		Memory.programBeginning = programBeginning;
		Memory.programData = new String[programSize];
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
	public static Short[] readDataBlock(int wordAddress) throws InvalidReadException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidReadException("Target address is out of memory space");
		} else if (wordAddress >= programBeginning && wordAddress <= programBeginning + programSize) {
			throw new InvalidReadException(
					String.format("Attempt to read data from instruction space for address %d", wordAddress));
		}

		return Arrays.copyOfRange(memoryData, (wordAddress / blockSize) * blockSize,
				Math.min((wordAddress / blockSize) * blockSize + blockSize, memorySize));
	}

	/**
	 * Write data to specific address in the data space
	 * 
	 * @param wordAddress
	 * @param data
	 * @throws InvalidWriteException
	 *             if the address is in program space or out of memory space
	 */
	public static void writeDataEntry(int wordAddress, short data) throws InvalidWriteException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidWriteException("Target address is out of memory space");
		} else if (wordAddress >= programBeginning && wordAddress <= programBeginning + programSize) {
			throw new InvalidWriteException(
					String.format("Attempt to write data to instruction space for address %d", wordAddress));
		}

		memoryData[wordAddress] = data;
	}

	/**
	 * Reads the block that contains the provided word address from program
	 * space
	 * 
	 * @param wordAddress
	 *            the address of the target word
	 * @return the block that contains the given wordAddress
	 * @throws InvalidReadException
	 *             if the address is in data space or out of memory space
	 */
	public static String[] readInstructionBlock(int wordAddress) throws InvalidReadException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidReadException("Target address is out of memory space");
		} else if (wordAddress < programBeginning || wordAddress > programBeginning + programSize) {
			throw new InvalidReadException(
					String.format("Attempt to read instruction from data space for address %d", wordAddress));
		}

		return Arrays.copyOfRange(programData, (wordAddress - programBeginning - 1 / blockSize) * blockSize,
				Math.min((wordAddress - programBeginning - 1 / blockSize) * blockSize + blockSize, memorySize));
	}

	/**
	 * Write instruction to specific address in the program space
	 * 
	 * @param wordAddress
	 * @param instruction
	 * @throws InvalidWriteException
	 *             if the address is in data space or out of memory space
	 */
	public static void writeProgramEntry(int wordAddress, String instruction) throws InvalidWriteException {
		if (wordAddress > memorySize || wordAddress < 0) {
			throw new InvalidWriteException("Target address is out of memory space");
		} else if (wordAddress < programBeginning || wordAddress > programBeginning + programSize) {
			throw new InvalidWriteException(
					String.format("Attempt to write instruction to data space for address %d", wordAddress));
		}

		programData[wordAddress - programBeginning - 1] = instruction;
	}

	// getters and setters

	public static Short[] getMemoryData() {
		return memoryData;
	}

	public static void setMemoryData(Short[] memoryData) {
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

	public static int getProgramBeginning() {
		return programBeginning;
	}

	public static void setProgramBeginning(int programBeginning) {
		Memory.programBeginning = programBeginning;
	}

}
