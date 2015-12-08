package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.registers.RegisterFile;

public class Processor {
	private static int clock = 0;
	// max number of instructions to issue in 1 cycle
	private int maxIssuesPerCycle, maxFetchesPerCycle, instructionQueueMaxSize;

	private short pc;

	private boolean halted;

	private LinkedList<Instruction> instructionsQueue;

	public Processor(int m, int instructionQueueSize)
			throws InvalidReadException, InvalidWriteException {
		this.maxIssuesPerCycle = m;
		this.maxFetchesPerCycle = m;
		this.pc = 0;
		this.halted = false;
		this.instructionQueueMaxSize = instructionQueueSize;
		this.instructionsQueue = new LinkedList<>();
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

	public int getInstructionQueueSize() {
		return instructionQueueMaxSize;
	}

	public void setInstructionQueueSize(int instructionQueueSize) {
		this.instructionQueueMaxSize = instructionQueueSize;
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

	public int getMaxFetchesPerCycle() {
		return maxFetchesPerCycle;
	}

	public void setMaxFetchesPerCycle(int maxFetchesPerCycle) {
		this.maxFetchesPerCycle = maxFetchesPerCycle;
	}

	public boolean isHalted() {
		return halted;
	}

	public void setHalted(boolean halt) {
		this.halted = halt;
	}

	private void incrementPc(int value) {
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

		int numOfFetches = 0;
		Instruction insruction;
		while (instructionsQueue.size() < instructionQueueMaxSize
				&& numOfFetches < maxFetchesPerCycle
				&& pc < Memory.getProgramSize()) {
			insruction = new Instruction(Caches.fetchInstruction(pc), pc);
			instructionsQueue.addLast(insruction);
			numOfFetches++;
			updatePC(insruction);
		}

		int numOfIssues = 0;
		while (instructionsQueue.size() > 0 && numOfIssues < maxIssuesPerCycle) {
			insruction = instructionsQueue.getFirst();
			if (issueInstruction(insruction)) {
				numOfIssues++;
				instructionsQueue.removeFirst().setIssuedTime(clock);
			} else {
				// Couldn't issue , stall
				break;
			}
		}

		clock++;
	}

	private boolean issueInstruction(Instruction instruction) {
		// Find a reservation station and an ROB entry for the instruction
		if (Parser.checkTypeMult(instruction.getType())) {

		} else if (Parser.checTypeArithmetic(instruction.getType())) {

		} else if (Parser.checTypeImmArithmetic(instruction.getType())) {

		} else if (Parser.checkTypeLoadStore(instruction.getType())) {

		} else if (Parser.checkTypeCondBranch(instruction.getType())) {

		} else if (Parser.checkTypeCall(instruction.getType())) {

		} else {
			// JMP / RET
			return true;
		}
		return false;
	}

	private void updatePC(Instruction fetchedInsruction) {
		int imm = fetchedInsruction.getImm();
		if (Parser.checkTypeCondBranch(fetchedInsruction.getType())) {
			if (imm < 0) {
				// take the branch
				incrementPc(1 + imm);
			} else {
				incrementPc(1);
			}
		} else if (Parser.checkTypeUncondBranch(fetchedInsruction.getType())) {

			int regNum = fetchedInsruction.getRd();
			short regValue = RegisterFile.getCorrectRegisterData(regNum);
			incrementPc(1 + regValue + imm);

		} else if (Parser.checkTypeCall(fetchedInsruction.getType())) {

			int regNum = fetchedInsruction.getRs();
			short regValue = RegisterFile.getCorrectRegisterData(regNum);
			setPc(regValue);

		} else if (Parser.checkTypeRet(fetchedInsruction.getType())) {

			int regNum = fetchedInsruction.getRd();
			short regValue = RegisterFile.getCorrectRegisterData(regNum);
			setPc(regValue);

		} else {
			incrementPc(1);
		}
	}

}
