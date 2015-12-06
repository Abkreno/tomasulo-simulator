package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.cache.CacheBlock;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;

public class Processor {
	private AddUnit[] addUnits;
	private MultUnit[] multUnits;
	private LoadUnit[] loadUnits;
	private StoreUnit[] storeUnits;
	private short pc;

	public Processor() throws InvalidReadException, InvalidWriteException {
		this.pc = 0;
	}

	public short getPc() {
		return pc;
	}

	public void setPc(short pc) {
		this.pc = pc;
	}

	public Short fetchData(short address) throws InvalidReadException,
			InvalidWriteException {
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getDataCaches());
		short offset = (short) (address % block.getSize());
		Short data = (Short) block.getEntries()[offset].getData();
		return data;
	}

	public String fetchInstruction(short instructionNumber)
			throws InvalidReadException, InvalidWriteException {
		short address = (short) (instructionNumber + Memory
				.getProgramBeginning());
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getInstructionCaches());
		short offset = (short) (address % block.getSize());
		String instruction = (String) block.getEntries()[offset].getData();
		return instruction;
	}

	public void writeData(short address, Short data)
			throws InvalidReadException, InvalidWriteException {
		Caches.writeCacheBlock(address, 0, data, Caches.getDataCaches());
	}

	public void writeInstruction(short instructionNumber, Short data)
			throws InvalidReadException, InvalidWriteException {
		short address = (short) (instructionNumber + Memory
				.getProgramBeginning());
		Caches.writeCacheBlock(address, 0, data, Caches.getInstructionCaches());
	}

	public void nextClockCycle() throws InvalidReadException,
			InvalidWriteException {
		String instruction = fetchInstruction(pc);
	}
}
