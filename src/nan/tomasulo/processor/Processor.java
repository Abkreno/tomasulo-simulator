package nan.tomasulo.processor;

import java.util.LinkedList;

import nan.tomasulo.Parser;
import nan.tomasulo.Simulator;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.AddUnit;
import nan.tomasulo.reservation_stations.BranchUnit;
import nan.tomasulo.reservation_stations.CallUnit;
import nan.tomasulo.reservation_stations.FunctionalUnits;
import nan.tomasulo.reservation_stations.JumpUnit;
import nan.tomasulo.reservation_stations.LoadUnit;
import nan.tomasulo.reservation_stations.LogicalUnit;
import nan.tomasulo.reservation_stations.MultUnit;
import nan.tomasulo.reservation_stations.ReservationStation;
import nan.tomasulo.reservation_stations.StoreUnit;

public class Processor {
	private static boolean reset;
	private static short correctAddress;
	private static int clock = 1;

	private int pipeLineWidth, instructionQueueMaxSize;
	private int totalInstructions, totalBranches, missPredictedBranches;
	private short pc;

	private boolean halted;

	private LinkedList<Instruction> instructionsQueue, finishedInstructions;

	private static LinkedList<ReservationStation> reservationStationsQueue = new LinkedList<>();;

	public Processor(int pipeLineWidth, int instructionQueueSize)
			throws InvalidReadException, InvalidWriteException {
		this.pipeLineWidth = pipeLineWidth;
		this.pc = 0;
		this.halted = false;
		this.instructionQueueMaxSize = instructionQueueSize;
		this.instructionsQueue = new LinkedList<>();
		this.finishedInstructions = new LinkedList<>();
		this.totalBranches = 0;
		this.missPredictedBranches = 0;
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

	public int getTotalBranches() {
		return totalBranches;
	}

	public void setTotalBranches(int totalBranches) {
		this.totalBranches = totalBranches;
	}

	public int getMissPredictedBranches() {
		return missPredictedBranches;
	}

	public void setMissPredictedBranches(int missPredictedBranches) {
		this.missPredictedBranches = missPredictedBranches;
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

	public LinkedList<Instruction> getFinishedInstructions() {
		return finishedInstructions;
	}

	public void setFinishedInstructions(
			LinkedList<Instruction> finishedInstructions) {
		this.finishedInstructions = finishedInstructions;
	}

	public double getBranchMissPredictionPercentage() {
		if (totalBranches == 0)
			return 0;
		return ((double) missPredictedBranches) / ((double) totalBranches)
				* 100.0;
	}

	public double getIPC() {
		return ((double) totalInstructions) / ((double) Math.max(clock - 2, 1));
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
		int numOfIssues = 0;
		Instruction instruction;
		while (instructionsQueue.size() < instructionQueueMaxSize
				&& numOfFetches < pipeLineWidth && pc < Memory.getProgramSize()) {
			if (numOfIssues < pipeLineWidth && instructionsQueue.size() > 0
					&& issueInstruction(instructionsQueue.getFirst())) {
				numOfIssues++;
				instructionsQueue.removeFirst().setIssuedTime(clock);
			} else if (instructionsQueue.size() > 0) {
				// Couldn't issue , stall
				break;
			}
			instruction = new Instruction(Caches.fetchInstruction(pc), pc);
			instructionsQueue.addLast(instruction);
			numOfFetches++;

			updatePC(instruction);
		}

		while (instructionsQueue.size() > 0 && numOfIssues < pipeLineWidth) {
			instruction = instructionsQueue.getFirst();
			if (issueInstruction(instruction)) {
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
				if (Simulator.console)
					System.out.println(currStation.getInstruction().getLog());
				finishedInstructions.add(currStation.getInstruction());
				if (Parser.checkTypeCondBranch(currStation.getOperation()))
					totalBranches++;
				totalInstructions++;
				currStation.reset();
				reservationStationsQueue.remove(i);
				i--;
			} else {
				currStation.update();
				if (reset) {
					reset = false;
					while (reservationStationsQueue.size() > 1)
						reservationStationsQueue.removeLast().reset();
					while (!instructionsQueue.isEmpty())
						instructionsQueue.remove();
					setPc(correctAddress);
					missPredictedBranches++;
					break;
				}
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
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		CallUnit[] callUnits = FunctionalUnits.getCallUnits();
		for (int i = 0; i < callUnits.length; i++) {
			if (!callUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				callUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(callUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueCondBranchInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		BranchUnit[] branchUnits = FunctionalUnits.getBranchUnits();
		for (int i = 0; i < branchUnits.length; i++) {
			if (!branchUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				branchUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(branchUnits[i]);
				return true;
			}
		}
		return false;
	}

	private boolean issueJumpInstruction(Instruction instruction) {
		if (ReorderBuffer.getFreeSlots() == 0)
			return false;
		JumpUnit[] jumpUnits = FunctionalUnits.getJumpUnits();
		for (int i = 0; i < jumpUnits.length; i++) {
			if (!jumpUnits[i].isBusy()) {
				int robEntry = ReorderBuffer.reserveSlot();
				jumpUnits[i].reserve(instruction, robEntry);
				reservationStationsQueue.add(jumpUnits[i]);
				return true;
			}
		}
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
			return issueJumpInstruction(instruction);
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

	public static void resetExectution(short address) {
		reset = true;
		correctAddress = address;
	}

	public static void setClock(int i) {
		clock = i;
	}

}
