package nan.tomasulo.reservation_stations;

public class FunctionalUnits {
	private static AddUnit[] addUnits;
	private static MultUnit[] multUnits;
	private static LoadUnit[] loadUnits;
	private static StoreUnit[] storeUnits;
	private static BranchUnit[] branchUnits;
	private static LogicalUnit[] logicalUnits;
	private static CallUnit[] callUnits;
	private static JumpUnit[] jumpUnits;
	private static ReservationStation[] reservationStations;

	public static void initFunctionalUnits(int numOfAddU, int addUnitsDelay,
			int numOfMultU, int multUnitsDelay, int numOfLoadU,
			int numOfStoreU, int numOfBranchU, int branchUnitsDelay,
			int numOfLogicU, int logicUnitsdelay, int numOfCallUnits,
			int callUnitsDelay, int numOfJumpUnits, int jumpUnitsDelay) {
		reservationStations = new ReservationStation[numOfAddU + numOfMultU
				+ numOfLoadU + numOfStoreU + numOfBranchU + numOfLogicU
				+ numOfCallUnits + numOfJumpUnits];
		int c = 0;
		addUnits = new AddUnit[numOfAddU];
		for (int i = 0; i < addUnits.length; i++) {
			addUnits[i] = new AddUnit(addUnitsDelay);
			reservationStations[c++] = addUnits[i];
		}
		multUnits = new MultUnit[numOfMultU];
		for (int i = 0; i < multUnits.length; i++) {
			multUnits[i] = new MultUnit(multUnitsDelay);
			reservationStations[c++] = multUnits[i];
		}
		storeUnits = new StoreUnit[numOfStoreU];
		for (int i = 0; i < storeUnits.length; i++) {
			storeUnits[i] = new StoreUnit();
			reservationStations[c++] = storeUnits[i];
		}
		loadUnits = new LoadUnit[numOfLoadU];
		for (int i = 0; i < loadUnits.length; i++) {
			loadUnits[i] = new LoadUnit();
			reservationStations[c++] = loadUnits[i];
		}
		branchUnits = new BranchUnit[numOfBranchU];
		for (int i = 0; i < branchUnits.length; i++) {
			branchUnits[i] = new BranchUnit(branchUnitsDelay);
			reservationStations[c++] = branchUnits[i];
		}
		logicalUnits = new LogicalUnit[numOfLogicU];
		for (int i = 0; i < logicalUnits.length; i++) {
			logicalUnits[i] = new LogicalUnit(logicUnitsdelay);
			reservationStations[c++] = logicalUnits[i];
		}
		callUnits = new CallUnit[numOfCallUnits];
		for (int i = 0; i < callUnits.length; i++) {
			callUnits[i] = new CallUnit(callUnitsDelay);
			reservationStations[c++] = callUnits[i];
		}
		jumpUnits = new JumpUnit[numOfJumpUnits];
		for (int i = 0; i < jumpUnits.length; i++) {
			jumpUnits[i] = new JumpUnit(jumpUnitsDelay);
			reservationStations[c++] = jumpUnits[i];
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

	public static ReservationStation[] getReservationStations() {
		return reservationStations;
	}

	public static LogicalUnit[] getLogicalUnits() {
		return logicalUnits;
	}

	public static CallUnit[] getCallUnits() {
		return callUnits;
	}

	public static JumpUnit[] getJumpUnits() {
		return jumpUnits;
	}
}
