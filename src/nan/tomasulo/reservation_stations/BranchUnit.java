package nan.tomasulo.reservation_stations;

public class BranchUnit extends ReservationStation {
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
	public void resetTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

}
