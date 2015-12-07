package nan.tomasulo.reservation_stations;

public class FunctionalUnits {
	private static AddUnit[] addUnits;
	private static MultUnit[] multUnits;
	private static LoadUnit[] loadUnits;
	private static StoreUnit[] storeUnits;
	private static BranchUnit[] branchUnits;

	public static void initFunctionalUnits(int numOfAddU, int numOfMultU,
			int numOfLoadU, int numOfStoreU, int numOfBranchU) {
		addUnits = new AddUnit[numOfAddU];
		for (int i = 0; i < addUnits.length; i++) {
			addUnits[i] = new AddUnit();
		}
		multUnits = new MultUnit[numOfMultU];
		for (int i = 0; i < multUnits.length; i++) {
			multUnits[i] = new MultUnit();
		}
		storeUnits = new StoreUnit[numOfStoreU];
		for (int i = 0; i < storeUnits.length; i++) {
			storeUnits[i] = new StoreUnit();
		}
		loadUnits = new LoadUnit[numOfLoadU];
		for (int i = 0; i < loadUnits.length; i++) {
			loadUnits[i] = new LoadUnit();
		}
		branchUnits = new BranchUnit[numOfBranchU];
		for (int i = 0; i < branchUnits.length; i++) {
			branchUnits[i] = new BranchUnit();
		}
	}

	public static AddUnit[] getAddUnits() {
		return addUnits;
	}

	public static MultUnit[] getMultUnits() {
		return multUnits;
	}

	public static LoadUnit[] getLoadUnits() {
		return loadUnits;
	}

	public static StoreUnit[] getStoreUnits() {
		return storeUnits;
	}

	public static BranchUnit[] getBranchUnits() {
		return branchUnits;
	}
}
