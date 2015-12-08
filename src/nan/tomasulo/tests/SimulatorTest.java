package nan.tomasulo.tests;

import java.util.Scanner;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

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

		FunctionalUnits.initFunctionalUnits(2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
		// sherif
		// salama
		// is
		// here

		ReorderBuffer.init(4);
		RegisterStat.init(8);

		Parser.copyProgramToMemory("program_2.in");
		Processor p = new Processor(maxIssuesPerCycle, 4);
		Scanner sc = new Scanner(System.in);
		while (true) {
			p.nextClockCycle();
			if (p.isHalted())
				break;
			sc.nextLine();
		}
	}
}
