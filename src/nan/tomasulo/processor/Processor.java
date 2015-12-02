package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.cache.CacheBlock;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.memory.Memory;

public class Processor {
	LinkedList<Process> processes;
	LinkedList<ReservationStation> reservationStations;
	RegisterResultStatus registerResultStatus;

	public Processor() {

	}

	public Short fetchData(short address) throws InvalidReadException {
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getDataCaches());
		short offset = (short) (address % block.getSize());
		Short data = (Short) block.getEntries()[offset].getData();
		return data;
	}

	public String fetchInstruction(short instructionNumber)
			throws InvalidReadException {
		short address = instructionNumber + Memory.getProgramBeginning();
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getInstructionCaches());
		short offset = (short) (address % block.getSize());
		String instruction = (String) block.getEntries()[offset].getData();
		return instruction;
	}
}
