package nan.tomasulo.reservation_stations;

import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ROBEntry;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class CallUnit extends ReservationStation {

	public CallUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public void update() {
		if (getCurrStage() == ISSUED) {
			if (getQj() == -1) {
				setCurrStage(EXECUTE);
			}
		} else if (getCurrStage() == EXECUTE) {
			setTimer(getTimer() - 1);
			if (getTimer() == 0) {
				setResult((short) (getInstruction().getAddress() + 1));
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
				if (RegisterStat.getRegisterROBEntryNumber(getInstruction()
						.getRd()) == getDst()) {
					RegisterStat.updateRegisterStats(getInstruction().getRd(),
							-1);
				}
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

		setBusy(true);
		setDst(robEntry);
		setTimer(getExecutionTime());
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
		RegisterStat.updateRegisterStats(instruction.getRd(), robEntry);
		ReorderBuffer.getEntries()[getDst()].setCorrectValue((short) (getInstruction().getAddress()+1));
	}

}
