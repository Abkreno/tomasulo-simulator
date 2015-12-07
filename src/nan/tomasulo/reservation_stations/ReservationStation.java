package nan.tomasulo.reservation_stations;

import nan.tomasulo.instructions.Instruction;

public abstract class ReservationStation {
	private int vj, vk; // values of source operands
	private int qj, qv; // values to be written to source operands
	private int id, robEntry;
	private int timer;
	private boolean busy = false;
	private String operation;
	private static int stationID = 1;

	public ReservationStation() {
		this.id = stationID++;
		this.vj = 0;
		this.vk = 0;
		this.qj = 0;
		this.qv = 0;
		this.busy = false;
	}

	public int getVj() {
		return vj;
	}

	public void setVj(int vj) {
		this.vj = vj;
	}

	public int getVk() {
		return vk;
	}

	public void setVk(int vk) {
		this.vk = vk;
	}

	public int getQj() {
		return qj;
	}

	public void setQj(int qj) {
		this.qj = qj;
	}

	public int getQv() {
		return qv;
	}

	public void setQv(int qv) {
		this.qv = qv;
	}

	public int getIndex() {
		return id;
	}

	public void setIndex(int index) {
		this.id = index;
	}

	public int getRobEntry() {
		return robEntry;
	}

	public void setRobEntry(int robEntry) {
		this.robEntry = robEntry;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public abstract void resetTimer();

	public int getTimer() {
		return timer;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public abstract int execute();

	public abstract void reserve(Instruction instruction);
}
