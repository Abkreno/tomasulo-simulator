package nan.tomasulo.processor;

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

	private boolean halted;

	public Processor(int maxIssuesPerCycle) throws InvalidReadException,
			InvalidWriteException {
		this.maxIssuesPerCycle = maxIssuesPerCycle;
		this.pc = 0;
		this.halted = false;
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

	public int getMaxIssuesPerCycle() {
		return maxIssuesPerCycle;
	}

	public void setMaxIssuesPerCycle(int maxIssuesPerCycle) {
		this.maxIssuesPerCycle = maxIssuesPerCycle;
	}

	public boolean isHalted() {
		return halted;
	}

	public void setHalted(boolean halt) {
		this.halted = halt;
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
				currentInstruction = new Instruction(
						Caches.fetchInstruction(pc));
			} else {
				// failed to issue the current instruction stall the pc
				break;
			}
		}
	}

	private boolean issueInstruction(Instruction currentInstruction) {
		// TODO Auto-generated method stub
		return false;
	}

}
