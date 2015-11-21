package nan.tomasulo.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import nan.tomasulo.cache.CacheEntry;

public class CacheEntryTests {

	@Test
	public void testInitializeArray() {
		CacheEntry[] entries = new CacheEntry[100];

		for (int i = 0; i < entries.length; i++) {
			entries[i] = new CacheEntry();
		}

		for (int i = 0; i < entries.length; i++) {
			assertTrue(entries[i].getData() == 0);
			assertTrue(entries[i].getTag() == -1);
			assertFalse(entries[i].isValid());
			assertFalse(entries[i].isDirty());
		}
	}
	

}
