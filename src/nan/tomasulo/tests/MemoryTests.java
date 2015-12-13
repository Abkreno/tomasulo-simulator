package nan.tomasulo.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;

public class MemoryTests {

	@Test
	public void testReadFromDataSpace() throws InvalidReadException {
		Memory.init(16);

		assertArrayEquals(new Short[16], Memory.readDataBlock(1));

	}

	@Test
	public void testWriteToDataSpace() throws InvalidWriteException,
			InvalidReadException {
		Memory.init(16);

		for (int i = 0; i < 32; i++) {
			Memory.writeDataEntry(i, (short) (i * 2));
		}

		Short[] b1 = new Short[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22,
				24, 26, 28, 30 };
		Short[] b2 = new Short[16];

		for (int i = 0; i < b2.length; i++) {
			b2[i] = (short) ((short) 32 + (i * 2));
		}

		for (int i = 0; i < 16; i++) {
			assertArrayEquals(b1, Memory.readDataBlock(i));
		}
		for (int i = 16; i < 32; i++) {
			assertArrayEquals(b2, Memory.readDataBlock(i));
		}

	}

}
