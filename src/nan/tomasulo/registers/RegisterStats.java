package nan.tomasulo.registers;

public class RegisterStats {
	private static int[] registerStats = new int[8];

	public static int getRegisterStats(int index) {
		return registerStats[index];
	}

	public static void updateRegisterStats(int index, int value) {
		RegisterStats.registerStats[index] = value;
	}

}
