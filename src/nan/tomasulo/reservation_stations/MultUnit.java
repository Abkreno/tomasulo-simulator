package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;

public class MultUnit extends ReservationStation {

	public MultUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
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

		int dstRegROBEntry = RegisterStat.getRegisterROBEntry(instruction
				.getRd());
		if (dstRegROBEntry == -1) {
			setVk(RegisterFile.getRegisterData(instruction.getRd()));
			setQk(-1);
		} else {
			setQk(dstRegROBEntry);
		}

		setBusy(true);
		setDst(robEntry);
		setOperation(instruction.getType());
		RegisterStat.updateRegisterStats(getDst(), robEntry);
	}

}