package nan.tomasulo.reservation_stations;

import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class MultUnit extends ReservationStation {

	public MultUnit(int executionTime) {
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
				setResult((short) (getVj() * getVk()));
				setCurrStage(WRITE_BACK);
				getInstruction().setExecutedTime(Processor.getClock());
			}
		} else if (getCurrStage() == WRITE_BACK) {
			if (CommonDataBus.deliver(getDst(), getResult())) {
				ReorderBuffer.getEntries()[getDst()].setValue(getResult());
				ReorderBuffer.getEntries()[getDst()].setReady(true);
				setCurrStage(COMMIT);
				getInstruction().setWrittenTime(Processor.getClock());
			}
		} else if (getCurrStage() == COMMIT) {
			if (ReorderBuffer.emptySlot(getDst())) {
				RegisterFile.setRegisterData(getInstruction().getRd(),
						getResult());
				getInstruction().setCommitedTime(Processor.getClock());
				ReorderBuffer.getEntries()[getDst()].resetEntry();
				reset();
			}
		}
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		int srcRegROBEntry = RegisterStat.getRegisterROBEntry(instruction
				.getRs());
		if (srcRegROBEntry == -1) {
			setVj(RegisterFile.getRegisterData(instruction.getRs()));
			setQj(-1);
		} else {
			setQj(srcRegROBEntry);
		}

		int tmpRegROBEntry = RegisterStat.getRegisterROBEntry(instruction
				.getRt());
		if (tmpRegROBEntry == -1) {
			setVk(RegisterFile.getRegisterData(instruction.getRt()));
			setQk(-1);
		} else {
			setQk(tmpRegROBEntry);
		}

		setBusy(true);
		setDst(robEntry);
		setTimer(getExecutionTime());
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
		RegisterStat.updateRegisterStats(instruction.getRd(), robEntry);
	}

}