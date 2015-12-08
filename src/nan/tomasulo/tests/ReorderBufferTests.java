package nan.tomasulo.tests;

import static org.junit.Assert.*;
import nan.tomasulo.reorderbuffer.ReorderBuffer;

import org.junit.Test;

public class ReorderBufferTests {
	@Test
	public void testReserveSlot() {
		ReorderBuffer.init(10);
		for (int i = 0; i < 10; i++) {
			int slot = ReorderBuffer.reserveSlot();
			assertTrue(slot == i);
		}
		assertTrue(ReorderBuffer.getFreeSlots() == 0);
	}

	@Test
	public void testEmptySlot() {
		ReorderBuffer.init(10);
		for (int i = 0; i < 10; i++) {
			ReorderBuffer.reserveSlot();
		}
		assertTrue(!ReorderBuffer.emptySlot(9));
		ReorderBuffer.getEntries()[0].setReady(true);
		assertTrue(ReorderBuffer.emptySlot(0));
	}
}
