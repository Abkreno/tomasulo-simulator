package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.AddUnit;
import nan.tomasulo.reservation_stations.FunctionalUnits;
import nan.tomasulo.reservation_stations.LoadUnit;
import nan.tomasulo.reservation_stations.LogicalUnit;
import nan.tomasulo.reservation_stations.MultUnit;
import nan.tomasulo.reservation_stations.ReservationStation;
import nan.tomasulo.reservation_stations.StoreUnit;

public class Processor {
	private static int clock = 1;

	private int pipeLineWidth, instructionQueueMaxSize;

	private short pc;

	private boolean halted;

	private LinkedList<Instruction> instructionsQueue;

	private static LinkedList<ReservationStation> reservationStationsQueue = new LinkedList<>();;

	public Processor(int pipeLineWidth, int instructionQueueSize)
			throws InvalidReadException, InvalidWriteException {
		this.pipeLineWidth = pipeLineWidth;
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

	public static LinkedList<ReservationStation> getReservationStationsQueue() {
		return reservationStationsQueue;
	}

	public int getMaxIssuesPerC() {
		return pipeLineWidth;
	}

	public int getInstructionQueueSize() {
		return instructionQueueMaxSize;
	}

	public void setInstructionQueueSize(int instructionQueueSize) {
		this.instructionQueueMaxSize = instructionQueueSize;
	}

	public void setMaxIssuesPerC(int maxIssuesPerC) {
		this.pipeLineWidth = maxIssuesPerC;
	}

	public short getPc() {
		return pc;
	}

	public void setPc(short pc) {
		this.pc = pc;
	}

	public int getPipeLineWidth() {
		return pipeLineWidth;
	}

	public void setPipeLineWidth(int pipeLineWidth) {
		this.pipeLineWidth = pipeLineWidth;
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
		CommonDataBus.resetCommonDataBus();
		ReorderBuffer.resetCommitsPerCycle();
		int numOfFetches = 0;
		Instruction insruction;
		while (instructionsQueue.size() < instructionQueueMaxSize
				&& numOfFetches < pipeLineWidth && pc < Memory.getProgramSize()) {
			insruction = new Instruction(Caches.fetchInstruction(pc), pc);
			instructionsQueue.addLast(insruction);
			numOfFetches++;
			updatePC(insruction);
		}

		int numOfIssues = 0;
		while (instructionsQueue.size() > 0 && numOfIssues < pipeLineWidth) {
			insruction = instructionsQueue.getFirst();
			if (issueInstruction(insruction)) {
				numOfIssues++;
				instructionsQueue.removeFirst().setIssuedTime(clock);
			} else {
				// Couldn't issue , stall
				break;
			}
		}

		for (int i = 0; i < reservationStationsQueue.size(); i++) {
			ReservationStation currStation = reservationStationsQueue.get(i);
			if (currStation.getCurrStage() == ReservationStation.FINISHED) {
				// Printing log for commited instruction
				System.out.println(currStation.getInstruction().getLog());
				currStation.reset();
				reservationStationsQueue.remove(i);
				i--;
			} else {
				currStation.update();
			}
		}
		if (instructionsQueue.isEmpty() && pc >= Memory.getProgramSize()
				&& reservationStationsQueue.isEmpty())
			setHalted(true);
		clock++;
	}

	private boolean issueMultInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		MultUnit[] multUnits = FunctionalUnits.getMultUnits();
		for (int i = 0; i < multUnits.length; i++) {
			if (!multUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				multUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(multUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueArithmeticInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		AddUnit[] addUnits = FunctionalUnits.getAddUnits();
		for (int i = 0; i < addUnits.length; i++) {
			if (!addUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				addUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(addUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueLogicalInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		LogicalUnit[] logicalUnits = FunctionalUnits.getLogicalUnits();
		for (int i = 0; i < logicalUnits.length; i++) {
			if (!logicalUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				logicalUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(logicalUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueStoreInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		StoreUnit[] storeUnits = FunctionalUnits.getStoreUnits();
		for (int i = 0; i < storeUnits.length; i++) {
			if (!storeUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				storeUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(storeUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueLoadInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		LoadUnit[] loadUnits = FunctionalUnits.getLoadUnits();
		for (int i = 0; i < loadUnits.length; i++) {
			if (!loadUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				loadUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(loadUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueCallInstruction(Instruction instruction) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean issueCondBranchInstruction(Instruction instruction) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean issueInstruction(Instruction instruction) {
		// Find a reservation station and an ROB entry for the instruction
		if (Parser.checkTypeMult(instruction.getType())) {
			return issueMultInstruction(instruction);
		} else if (Parser.checkTypeLogical(instruction.getType())) {
			return issueLogicalInstruction(instruction);
		} else if (Parser.checkTypeImmArithmetic(instruction.getType())
				|| Parser.checkTypeArithmetic(instruction.getType())) {
			return issueArithmeticInstruction(instruction);
		} else if (Parser.checkTypeLoadStore(instruction.getType())) {
			return instruction.getType().equals("LW") ? issueLoadInstruction(instruction)
					: issueStoreInstruction(instruction);
		} else if (Parser.checkTypeCondBranch(instruction.getType())) {
			return issueCondBranchInstruction(instruction);
		} else if (Parser.checkTypeCall(instruction.getType())) {
			return issueCallInstruction(instruction);
		} else {
			// JMP / RET
			return true;
		}
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

	public static int getClock() {
		return clock;
	}

}
