package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public class BranchUnit extends ReservationStation {
	
	public static int executionTime;
	
	private int predicedAddress, correctAddress;

	public BranchUnit() {
		super();

	}

	public int getPredictedAddress() {
		return predicedAddress;
	}

	public void setPredicedAddress(int predictionAddress) {
		this.predicedAddress = predictionAddress;
	}

	public int getCorrectAddress() {
		return correctAddress;
	}

	public void setCorrectAddress(int correctAddress) {
		this.correctAddress = correctAddress;
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reserve(Instruction instruction) {
		// TODO Auto-generated method stub

	}

}
