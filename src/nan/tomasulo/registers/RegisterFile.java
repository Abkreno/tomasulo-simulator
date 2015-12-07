package nan.tomasulo.registers;

import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

public class RegisterFile {
	private static Register[] registers = new Register[8];

	public static void setRegisterData(int index, short data) {
		if (index == 0)
			return;
		registers[index].setData(data);
	}

	public static short getRegisterData(int index) {
		return registers[index].getData();
	}

	public static short getCorrectRegisterData(int regNum) {
		int robEntry = RegisterStats.getRegisterStats(regNum);
		short regValue = 0;
		if (robEntry == 0) {
			// No ROB entry is using this register as destination so
			// use the value in register file
			regValue = RegisterFile.getRegisterData(regNum);
		} else {
			regValue = ReorderBuffer.getEntries()[regNum].getCorrectValue();
		}
		return regValue;
	}

}
