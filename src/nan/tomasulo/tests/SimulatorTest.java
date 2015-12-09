package nan.tomasulo.tests;

import java.util.Scanner;

import nan.tomasulo.Parser;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

public class SimulatorTest {
	static int memAccessDelay = 3;
	static int pipelineWidth = 4;

	public static void main(String[] args) throws InvalidReadException,
			InvalidWriteException {
		Memory.setAccessDelay(memAccessDelay);
		int[][] cachesInfo = new int[1][5];
		// S ,L ,m ,accessDelay , policy
		cachesInfo[0] = new int[] { 128, 16, 1, 1, 0 };
		Caches.initCaches(cachesInfo);

		FunctionalUnits.initFunctionalUnits(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
				2, 2);
		// sherif
		// salama
		// is
		// here
		ReorderBuffer.init(4, 1);
		RegisterStat.init(8);
		RegisterFile.init();
		CommonDataBus.setMaxNumOfWritesPerCycle(1);
		Parser.copyProgramToMemory("program_5.in");
		Processor p = new Processor(pipelineWidth, 4);
		Scanner sc = new Scanner(System.in);
		while (true) {
			p.nextClockCycle();
			if (p.isHalted())
				break;
			// sc.nextLine();
		}
		System.out.println(RegisterFile.getRegisterData(6));
	}
}
