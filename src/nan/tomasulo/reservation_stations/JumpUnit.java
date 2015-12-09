package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class JumpUnit extends ReservationStation {
	public JumpUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public void update() {
		if (getCurrStage() == ISSUED) {
			setCurrStage(EXECUTE);
		} else if (getCurrStage() == EXECUTE) {
			setTimer(getTimer() - 1);
			if (getTimer() == 0) {
				setCurrStage(WRITE_BACK);
				getInstruction().setExecutedTime(Processor.getClock());
			}
		} else if (getCurrStage() == WRITE_BACK) {
			ReorderBuffer.getEntries()[getDst()].setReady(true);
			setCurrStage(COMMIT);
			getInstruction().setWrittenTime(Processor.getClock());
		} else if (getCurrStage() == COMMIT) {
			if (ReorderBuffer.emptySlot(getDst())) {
				getInstruction().setCommitedTime(Processor.getClock());
				ReorderBuffer.getEntries()[getDst()].resetEntry();
				setCurrStage(FINISHED);
			}
		}
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		setBusy(true);
		setDst(robEntry);
		setTimer(getExecutionTime());
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
	}

}
