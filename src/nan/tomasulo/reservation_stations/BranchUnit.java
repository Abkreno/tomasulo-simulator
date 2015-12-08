package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public class BranchUnit extends ReservationStation {

	private int predicedAddress, correctAddress;

	public BranchUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
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
	public void reserve(Instruction instruction,int robEntry) {
		// TODO Auto-generated method stub

	}

}
