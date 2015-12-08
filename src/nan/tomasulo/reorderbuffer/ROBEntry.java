package nan.tomasulo.reorderbuffer;

public class ROBEntry {
	private short correctValue, value;
	private String type;
	private boolean ready;
	private int dstRegNumber;

	public ROBEntry() {
		this.correctValue = 0;
		this.value = 0;
		this.ready = false;
	}

	public short getCorrectValue() {
		return correctValue;
	}

	public void setCorrectValue(short correctValue) {
		this.correctValue = correctValue;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getDstRegNumber() {
		return dstRegNumber;
	}

	public void setDstRegNumber(int dstRegNumber) {
		this.dstRegNumber = dstRegNumber;
	}

	public void resetEntry() {
		this.ready = false;
		this.dstRegNumber = 0;
		this.correctValue = 0;
		this.type = null;
		this.value = 0;
	}

}