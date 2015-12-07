package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.reservation_stations.AddUnit;
import nan.tomasulo.reservation_stations.BranchUnit;
import nan.tomasulo.reservation_stations.LoadUnit;
import nan.tomasulo.reservation_stations.MultUnit;
import nan.tomasulo.reservation_stations.StoreUnit;

public class Processor {
	private AddUnit[] addUnits;
	private MultUnit[] multUnits;
	private LoadUnit[] loadUnits;
	private StoreUnit[] storeUnits;
	private BranchUnit[] branchUnits;

	// max number of instructions to issue in 1 cycle
	private int maxIssuesPerCycle;

	private short pc;

	private boolean halted;

	private LinkedList<Instruction> instructionsQueue;

	public Processor(int maxIssuesPerCycle, int numOfAddU, int numOfMultU,
			int numOfLoadU, int numOfStoreU, int numOfBranchU)
			throws InvalidReadException, InvalidWriteException {
		this.maxIssuesPerCycle = maxIssuesPerCycle;
		this.pc = 0;
		this.halted = false;
		this.instructionsQueue = new LinkedList<>();
		initFunctionalUnits(numOfAddU, numOfMultU, numOfLoadU, numOfStoreU, numOfBranchU);
	}

	private void initFunctionalUnits(int numOfAddU, int numOfMultU, int numOfLoadU,
			int numOfStoreU, int numOfBranchU) {
		this.addUnits = new AddUnit[numOfAddU];
		for (int i = 0; i < addUnits.length; i++) {
			addUnits[i] = new AddUnit();
		}
		this.multUnits = new MultUnit[numOfMultU];
		for (int i = 0; i < multUnits.length; i++) {
			multUnits[i] = new MultUnit();
		}
		this.storeUnits = new StoreUnit[numOfStoreU];
		for (int i = 0; i < storeUnits.length; i++) {
			storeUnits[i] = new StoreUnit();
		}
		this.loadUnits = new LoadUnit[numOfLoadU];
		for (int i = 0; i < loadUnits.length; i++) {
			loadUnits[i] = new LoadUnit();
		}
		this.branchUnits = new BranchUnit[numOfBranchU];
		for (int i = 0; i < branchUnits.length; i++) {
			branchUnits[i] = new BranchUnit();
		}
	}

	public LinkedList<Instruction> getInstructionsQueue() {
		return instructionsQueue;
	}

	public void setInstructionsQueue(LinkedList<Instruction> instructionsQueue) {
		this.instructionsQueue = instructionsQueue;
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
		Instruction fetchedInsruction;
		while (numOfIssues < maxIssuesPerCycle) {
			fetchedInsruction = new Instruction(Caches.fetchInstruction(pc));

			instructionsQueue.add(fetchedInsruction);
			numOfIssues++;
			updatePC(fetchedInsruction);

		}
	}

	private void updatePC(Instruction fetchedInsruction) {
		incrementPC(1);
		if (Parser.checkTypeCondBranch(fetchedInsruction.getType())) {

		} else if (Parser.checkTypeUncondBranch(fetchedInsruction.getType())) {

		} else if (Parser.checkTypeCall(fetchedInsruction.getType())) {

		} else if (Parser.checkTypeRet(fetchedInsruction.getType())) {

		} else {
		}
	}

	private boolean issueInstruction(Instruction currentInstruction) {
		return false;
	}

}
