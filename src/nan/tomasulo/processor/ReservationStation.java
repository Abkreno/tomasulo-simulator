package nan.tomasulo.processor;

public class ReservationStation {
	String name;
	String op;			// operation to perform
	String vj, vk;		// values of source operands
	String qj, qv;		// values to be written to source operands
	String a;			// address information for load and store
	boolean busy = false;
	public ReservationStation(String name, String op, String vj, String vk,
			String qj, String qv, String a, boolean busy) {
		super();
		this.name = name;
		this.op = op;
		this.vj = vj;
		this.vk = vk;
		this.qj = qj;
		this.qv = qv;
		this.a = a;
		this.busy = busy;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getVj() {
		return vj;
	}
	public void setVj(String vj) {
		this.vj = vj;
	}
	public String getVk() {
		return vk;
	}
	public void setVk(String vk) {
		this.vk = vk;
	}
	public String getQj() {
		return qj;
	}
	public void setQj(String qj) {
		this.qj = qj;
	}
	public String getQv() {
		return qv;
	}
	public void setQv(String qv) {
		this.qv = qv;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
