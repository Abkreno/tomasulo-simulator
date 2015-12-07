package nan.tomasulo.reorderbuffer;

public class ReorderBuffer {
	private int head, tail, freeSlots;
	private ROBEntry[] entries;

	private static class ROBEntry {

	}

	public ReorderBuffer(int size) {
		this.head = 0;
		this.tail = 0;
		this.freeSlots = size;
		this.entries = new ROBEntry[size];
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getTail() {
		return tail;
	}

	public void setTail(int tail) {
		this.tail = tail;
	}

	public int getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(int freeSlots) {
		this.freeSlots = freeSlots;
	}

	public ROBEntry[] getEntries() {
		return entries;
	}

	public void setEntries(ROBEntry[] entries) {
		this.entries = entries;
	}

}
