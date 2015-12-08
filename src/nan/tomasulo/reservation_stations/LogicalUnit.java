package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public class LogicalUnit extends ReservationStation {
	public LogicalUnit(int executionTime) {
		super();
		setExecutionTime(executionTime);
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reserve(Instruction instruction, int robEntry) {
		// TODO Auto-generated method stub

	}

}
