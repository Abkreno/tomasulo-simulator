package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ROBEntry;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class BranchUnit extends ReservationStation {
	private boolean prediction;
	private short correctAddress;

	public BranchUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public void update() {
		if (getCurrStage() == ISSUED) {
			if (getQj() == -1 && getQk() == -1) {
				setCurrStage(EXECUTE);
			}
		} else if (getCurrStage() == EXECUTE) {
			setTimer(getTimer() - 1);
			if (getTimer() == 0) {
				Instruction instruction = getInstruction();
				if (getVk() == getVj()) {
					correctAddress = (short) (instruction.getAddress() + 1 + instruction
							.getImm());
					prediction = instruction.getImm() < 0;
				} else {
					correctAddress = (short) (instruction.getAddress() + 1);
					prediction = instruction.getImm() >= 0;
				}
				ReorderBuffer.getEntries()[getDst()].setReady(true);
				getInstruction().setExecutedTime(Processor.getClock());
				getInstruction().setWrittenTime(Processor.getClock());
				setCurrStage(COMMIT);
			}
		} else if (getCurrStage() == COMMIT) {
			if (ReorderBuffer.emptySlot(getDst())) {
				if (!prediction) {
					ReorderBuffer.flush();
					RegisterStat.reset();
					Processor.resetExectution(correctAddress);
				} else {
					ReorderBuffer.getEntries()[getDst()].resetEntry();
					if (RegisterStat.getRegisterROBEntryNumber(getInstruction()
							.getRd()) == getDst()) {
						RegisterStat.updateRegisterStats(getInstruction()
								.getRd(), -1);
					}
				}
				getInstruction().setCommitedTime(Processor.getClock());
				setCurrStage(FINISHED);
			}
		}
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		int vj = 0;
		int srcRegROBEntry = RegisterStat.getRegisterROBEntryNumber(instruction
				.getRs());
		if (srcRegROBEntry == -1) {
			vj = RegisterFile.getRegisterData(instruction.getRs());
			setVj(vj);
			setQj(-1);
		} else {
			ROBEntry srcRegROB = ReorderBuffer.getEntries()[srcRegROBEntry];
			vj = srcRegROB.getCorrectValue();
			if (srcRegROB.isReady()) {
				setVj(srcRegROB.getValue());
				setQj(-1);
			} else {
				setQj(srcRegROBEntry);
			}
		}

		int vk = 0;
		int tmpRegROBEntry = RegisterStat.getRegisterROBEntryNumber(instruction
				.getRt());
		if (tmpRegROBEntry == -1) {
			vk = RegisterFile.getRegisterData(instruction.getRt());
			setVk(vk);
			setQk(-1);
		} else {
			ROBEntry tmpRegROB = ReorderBuffer.getEntries()[tmpRegROBEntry];
			vk = tmpRegROB.getCorrectValue();
			if (tmpRegROB.isReady()) {
				setVk(tmpRegROB.getValue());
				setQk(-1);
			} else {
				setQk(tmpRegROBEntry);
			}
		}

		setBusy(true);
		setDst(robEntry);
		setTimer(getExecutionTime());
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
	}

}
