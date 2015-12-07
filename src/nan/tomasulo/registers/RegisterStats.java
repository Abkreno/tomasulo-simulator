package nan.tomasulo.registers;

public class RegisterStats {
	private static int[] registerStats = new int[8];

	/**
	 * 
	 * @param registerNum
	 * @return the index of the ROB entry that is using this register as its
	 *         destination
	 */
	public static int getRegisterStats(int registerNum) {
		return registerStats[registerNum];
	}

	public static void updateRegisterStats(int index, int value) {
		RegisterStats.registerStats[index] = value;
	}

}
