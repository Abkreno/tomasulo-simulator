package nan.tomasulo.registers;

public class RegisterStat {
	private static int[] registerStats;

	public static void init(int size) {
		registerStats = new int[size];
		for (int i = 0; i < registerStats.length; i++) {
			registerStats[i] = -1;
		}
	}

	/**
	 * 
	 * @param registerNum
	 * @return the index of the ROB entry that is using this register as its
	 *         destination
	 */
	public static int getRegisterROBEntryNumber(int registerNum) {
		return registerStats[registerNum];
	}

	public static void updateRegisterStats(int index, int value) {
		RegisterStat.registerStats[index] = value;
	}

}
