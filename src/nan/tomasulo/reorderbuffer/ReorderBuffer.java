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

}
