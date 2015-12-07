package nan.tomasulo.registers;

public class RegisterFile {
	private static Register[] registers = new Register[8];

	public static void setRegister(int index, short data) {
		if (index == 0)
			return;
		registers[index].setData(data);
	}

	public static short getRegister(int index) {
		return registers[index].getData();
	}

}
