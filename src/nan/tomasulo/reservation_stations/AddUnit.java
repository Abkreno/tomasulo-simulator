package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public class AddUnit extends ReservationStation {

	public AddUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		// TODO Auto-generated method stub

	}

}