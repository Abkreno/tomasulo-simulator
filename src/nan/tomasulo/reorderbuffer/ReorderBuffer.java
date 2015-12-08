package nan.tomasulo.reorderbuffer;

public class ReorderBuffer {
	private static int head, tail, size, freeSlots;
	private static ROBEntry[] entries;

	public static void init(int s) {
		size = s;
		freeSlots = s;
		entries = new ROBEntry[s];
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

	public static int getSize() {
		return size;
	}

	public static int getFreeSlots() {
		return freeSlots;
	}

	public static ROBEntry[] getEntries() {
		return entries;
	}

	public static boolean emptySlot(int entry) {
		if (head != entry || !entries[entry].isReady())
			return false;
		entries[entry].resetEntry();
		head = (head + 1) % size;
		freeSlots++;
		return true;
	}

	public static int reserveSlot() {
		if (freeSlots == 0)
			return -1;
		int res = tail;
		tail = (tail + 1) % size;
		freeSlots--;
		return res;
	}
}
