package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStats;
import nan.tomasulo.reservation_stations.FunctionalUnits;

public class Processor {

	// max number of instructions to issue in 1 cycle
	private int maxIssuesPerCycle, instructionQueueSize;

	private short pc;

	private boolean halted;

	private LinkedList<Instruction> instructionsQueue;

	public Processor(int maxIssuesPerCycle, int instructionQueueSize)
			throws InvalidReadException, InvalidWriteException {
		this.maxIssuesPerCycle = maxIssuesPerCycle;
		this.pc = 0;
		this.halted = false;
		this.instructionQueueSize = instructionQueueSize;
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
		return instructionQueueSize;
	}

	public void setInstructionQueueSize(int instructionQueueSize) {
		this.instructionQueueSize = instructionQueueSize;
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
		int imm = fetchedInsruction.getImm();
		if (Parser.checkTypeCondBranch(fetchedInsruction.getType())) {
			if (imm < 0) {
				// take the branch
				incrementPC(1 + imm);
			} else {
				incrementPC(1);
			}
		} else if (Parser.checkTypeUncondBranch(fetchedInsruction.getType())) {

			int regNum = fetchedInsruction.getRd();
			short regValue = RegisterFile.getCorrectRegisterData(regNum);
			incrementPC(1 + regValue + imm);

		} else if (Parser.checkTypeCall(fetchedInsruction.getType())) {
			
		} else if (Parser.checkTypeRet(fetchedInsruction.getType())) {

		} else {
		}
	}

	private boolean issueInstruction(Instruction currentInstruction) {
		return false;
	}

}
