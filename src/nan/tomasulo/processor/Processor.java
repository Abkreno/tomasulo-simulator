package nan.tomasulo.processor;

import nan.tomasulo.cache.CacheBlock;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.reservation_stations.AddUnit;
import nan.tomasulo.reservation_stations.LoadUnit;
import nan.tomasulo.reservation_stations.MultUnit;
import nan.tomasulo.reservation_stations.StoreUnit;

public class Processor {
	private AddUnit[] addUnits;
	private MultUnit[] multUnits;
	private LoadUnit[] loadUnits;
	private StoreUnit[] storeUnits;
	private Instruction currentInstruction;

	// max number of instructions to issue in 1 cycle
	private int maxIssuesPerCycle;

	private short pc;

	public Processor() throws InvalidReadException, InvalidWriteException {
		this.pc = 0;
		this.currentInstruction = new Instruction();
	}

	public Instruction getCurrentInstruction() {
		return currentInstruction;
	}

	public void setCurrentInstruction(Instruction currentInstruction) {
		this.currentInstruction = currentInstruction;
	}

	public int getMaxIssuesPerC() {
		return maxIssuesPerCycle;
	}

	public void setMaxIssuesPerC(int maxIssuesPerC) {
		this.maxIssuesPerCycle = maxIssuesPerC;
	}

	public short getPc() {
		return pc;
	}

	public void setPc(short pc) {
		this.pc = pc;
	}

	public AddUnit[] getAddUnits() {
		return addUnits;
	}

	public void setAddUnits(AddUnit[] addUnits) {
		this.addUnits = addUnits;
	}

	public MultUnit[] getMultUnits() {
		return multUnits;
	}

	public void setMultUnits(MultUnit[] multUnits) {
		this.multUnits = multUnits;
	}

	public LoadUnit[] getLoadUnits() {
		return loadUnits;
	}

	public void setLoadUnits(LoadUnit[] loadUnits) {
		this.loadUnits = loadUnits;
	}

	public StoreUnit[] getStoreUnits() {
		return storeUnits;
	}

	public void setStoreUnits(StoreUnit[] storeUnits) {
		this.storeUnits = storeUnits;
	}

	private void incrementPC(int value) {
		this.pc += value;
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
		int numOfIssues = 0;
		while (numOfIssues < maxIssuesPerCycle) {
			if (currentInstruction.isIssued()
					|| issueInstruction(currentInstruction)) {
				numOfIssues++;
				incrementPC(1);
				currentInstruction = new Instruction(fetchInstruction(pc));
			} else {
				// failed to issue the currentInstruction stall the pc
				break;
			}
		}
	}

	private boolean issueInstruction(Instruction currentInstruction) {
		// TODO Auto-generated method stub
		return false;
	}

}
