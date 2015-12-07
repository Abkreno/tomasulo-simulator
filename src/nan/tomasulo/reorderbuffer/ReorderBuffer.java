package nan.tomasulo.reorderbuffer;

public class ReorderBuffer {
	private static int head, tail, freeSlots;
	private static ROBEntry[] entries;

	public void init(int size) {
		freeSlots = size;
		entries = new ROBEntry[size];
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new ROBEntry();
		}
		head = tail = 0;
	}

	public static int getHead() {
		return head;
	}

	public static int getTail() {
		return tail;
	}

	public static int getFreeSlots() {
		return freeSlots;
	}

	public static ROBEntry[] getEntries() {
		return entries;
	}

}
