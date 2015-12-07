package nan.tomasulo.tests;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;

public class SimulatorTest {
	static int memAccessDelay = 10;
	static int maxIssuesPerCycle = 1;

	public static void main(String[] args) throws InvalidReadException,
			InvalidWriteException {
		Memory.setAccessDelay(memAccessDelay);
		int[][] cachesInfo = new int[1][5];
		// S ,L ,m ,accessDelay , policy
		cachesInfo[0] = new int[] { 128, 16, 1, 1, 0 };
		Caches.initCaches(cachesInfo);
		Parser.copyProgramToMemory("program_1.in");
		Processor p = new Processor(maxIssuesPerCycle, 3, 3, 3, 3, 3);
		while (true) {
			p.nextClockCycle();
			Instruction in = new Instruction(Caches.fetchInstruction(p.getPc()));
			p.setPc((short) (p.getPc() + 1));
			System.out.println(in.toString());
			if (p.isHalted())
				break;
		}
	}
}
