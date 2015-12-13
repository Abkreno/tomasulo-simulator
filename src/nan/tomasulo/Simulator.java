package nan.tomasulo;

import java.util.Scanner;

import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

public class Simulator {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		getMemoryInputs(sc);
		Memory.init(16);
		Parser.copyProgramToMemory("program_1.in");

		getFuncUnitsInputs(sc);

		System.out.println("Enter ReorderBuffer Size :");
		int robSize = Integer.parseInt(sc.nextLine());
		System.out.println("Max Commits per cycle :");
		int maxCommits = Integer.parseInt(sc.nextLine());
		ReorderBuffer.init(robSize, maxCommits);
		RegisterStat.init(8);

		System.out.println("Enter Pipeline width :");
		int pipeLineWidth = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Instruction Buffer Size :");
		int maxNumOfInstInBuffer = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Number of Common data buses :");
		CommonDataBus
				.setMaxNumOfWritesPerCycle(Integer.parseInt(sc.nextLine()));

		Processor p = new Processor(pipeLineWidth, maxNumOfInstInBuffer);
		while (true) {
			p.nextClockCycle();
			Instruction in = new Instruction(
					Caches.fetchInstruction(p.getPc()), p.getPc());
			p.setPc((short) (p.getPc() + 1));
			System.out.println(in.toString());
			if (p.isHalted())
				break;
			sc.nextLine();
		}
		sc.close();
	}

	static void getMemoryInputs(Scanner sc) {
		System.out.println("Enter Memory Access Delay :");
		Memory.setAccessDelay(Integer.parseInt(sc.nextLine()));
		System.out.println("Enter Number of Caches :");
		int n = Integer.parseInt(sc.nextLine());
		int[][] cachesInfo = new int[n][5];
		String[] line;
		for (int i = 0; i < n; i++) {
			System.out.println("Enter the description of level " + (i + 1)
					+ " cache in the form S,L,m :");
			line = sc.nextLine().split(",");
			for (int j = 0; j < 3; j++) {
				cachesInfo[i][j] = Integer.parseInt(line[j]);
			}
			System.out.println("Enter Cache Access Delay (in cycles) :");
			cachesInfo[i][3] = Integer.parseInt(sc.nextLine());
			System.out
					.println("Enter Cache Write Policy 0 (for Write Through) / 1 (for Write Back): ");
			cachesInfo[i][4] = Integer.parseInt(sc.nextLine());
		}
		Caches.initCaches(cachesInfo);
	}

	static void getFuncUnitsInputs(Scanner sc) {
		System.out.println("Enter Number of AddUnits");
		int addUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter AddUnits Delay");
		int addUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of MultUnits");
		int multUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter MultUnits Delay");
		int multUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of LoadUnits");
		int loadUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of StoreUnits");
		int storeUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of BranchUnits");
		int branchUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter BranchUnits Delay");
		int branchUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Logical Units");
		int logicalUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Logical Units Delay");
		int logicalUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Call Units");
		int callUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Call Units Delay");
		int callUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Jump Units");
		int jumpUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Jump Units Delay");
		int jumpUnitsDelay = Integer.parseInt(sc.nextLine());

		FunctionalUnits.initFunctionalUnits(addUnits, addUnitsDelay, multUnits,
				multUnitsDelay, loadUnits, storeUnits, branchUnits,
				branchUnitsDelay, logicalUnits, logicalUnitsDelay, callUnits,
				callUnitsDelay, jumpUnits, jumpUnitsDelay);
	}
}
