package nan.tomasulo.reservation_stations;

import nan.tomasulo.Parser;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

public class AddUnit extends ReservationStation {

	public AddUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		int vj = 0;
		int srcRegROBEntry = RegisterStat.getRegisterROBEntry(instruction
				.getRs());
		if (srcRegROBEntry == -1) {
			vj = RegisterFile.getRegisterData(instruction.getRs());
			setVj(vj);
			setQj(-1);
		} else {
			vj = ReorderBuffer.getEntries()[srcRegROBEntry].getCorrectValue();
			setQj(srcRegROBEntry);
		}

		int vk = 0;
		if (Parser.checkTypeImmArithmetic(instruction.getType())) {
			vk = instruction.getImm();
			setVk(vk);
			setQk(-1);
		} else {
			int tmpRegROBEntry = RegisterStat.getRegisterROBEntry(instruction
					.getRt());
			if (tmpRegROBEntry == -1) {
				vk = RegisterFile.getRegisterData(instruction.getRt());
				setVk(vk);
				setQk(-1);
			} else {
				vk = ReorderBuffer.getEntries()[tmpRegROBEntry]
						.getCorrectValue();
				setQk(tmpRegROBEntry);
			}
		}

		setBusy(true);
		setDst(robEntry);
		setTimer(getExecutionTime());
		setOperation(instruction.getType());
		setInstruction(instruction);
		setCurrStage(ISSUED);
		RegisterStat.updateRegisterStats(instruction.getRd(), robEntry);
		ReorderBuffer.getEntries()[getDst()].setCorrectValue(calculate(vj, vk,
				getOperation()));

	}

}