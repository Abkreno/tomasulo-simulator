package nan.tomasulo;

import java.util.LinkedList;
import java.util.Scanner;

import nan.tomasulo.cache.Cache;
import nan.tomasulo.cache.Caches;
import nan.tomasulo.common_data_bus.CommonDataBus;
import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.gui.InputsWindow;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;
import nan.tomasulo.registers.RegisterFile;
import nan.tomasulo.registers.RegisterStat;
import nan.tomasulo.reorderbuffer.ReorderBuffer;
import nan.tomasulo.reservation_stations.FunctionalUnits;

public class Simulator {
	public static boolean console = false;

	public static void console() throws InvalidReadException,
			InvalidWriteException {
		Scanner sc = new Scanner(System.in);

		getMemoryInputs(sc);
		System.out.println("Enter Memory Block Size :");
		Memory.init(Integer.parseInt(sc.nextLine()));
		System.out.println("Enter program name :");
		Parser.copyProgramToMemory(sc.nextLine() + ".in");

		getFuncUnitsInputs(sc);

		System.out.println("Enter ReorderBuffer Size :");
		int robSize = Integer.parseInt(sc.nextLine());
		System.out.println("Max Commits per cycle :");
		int maxCommits = Integer.parseInt(sc.nextLine());
		ReorderBuffer.init(robSize, maxCommits);
		RegisterStat.init(8);
		RegisterFile.init();
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
			if (p.isHalted()) {
				displayResults(p);
				break;
			}
			// sc.nextLine();
		}
		sc.close();
	}

	private static void displayResults(Processor processor) {
		System.out.println("-------------Results------------");
		System.out.println("Total Execution Time = "
				+ Math.max(Processor.getClock() - 2, 1) + "");
		System.out.println("IPC = " + String.format("%.3f", processor.getIPC())
				+ "");
		System.out.println("Branch Miss Prediction Percentage = "
				+ String.format("%.3f",
						processor.getBranchMissPredictionPercentage()) + "%");
		System.out.println("Global AMAT = "
				+ String.format("%.3f", Caches.getAMAT(0)));
		LinkedList<Cache> caches = Caches.getDataCaches();
		for (int i = 0; i < caches.size(); i++) {
			System.out.println("Cache level " + (i + 1) + " Hit Ratio is : "
					+ String.format("%.3f", caches.get(i).getHitRatio()));
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 0 for console app or 1 for GUI");
		int n = sc.nextInt();
		sc.nextLine();
		if (n == 0) {
			console = true;
			console();
		} else {
			new InputsWindow();
		}
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
		System.out.println("Enter Number of AddUnits :");
		int addUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter AddUnits Delay :");
		int addUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of MultUnits :");
		int multUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter MultUnits Delay :");
		int multUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of LoadUnits :");
		int loadUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of StoreUnits :");
		int storeUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of BranchUnits :");
		int branchUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter BranchUnits Delay :");
		int branchUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Logical Units :");
		int logicalUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Logical Units Delay :");
		int logicalUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Call Units :");
		int callUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Call Units Delay :");
		int callUnitsDelay = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Number of Jump Units :");
		int jumpUnits = Integer.parseInt(sc.nextLine());

		System.out.println("Enter Jump Units Delay :");
		int jumpUnitsDelay = Integer.parseInt(sc.nextLine());

		FunctionalUnits.initFunctionalUnits(addUnits, addUnitsDelay, multUnits,
				multUnitsDelay, loadUnits, storeUnits, branchUnits,
				branchUnitsDelay, logicalUnits, logicalUnitsDelay, callUnits,
				callUnitsDelay, jumpUnits, jumpUnitsDelay);
	}
}
