package nan.tomasulo;

import java.util.Scanner;

import nan.tomasulo.cache.Caches;
import nan.tomasulo.instructions.Instruction;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;

public class Simulator {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
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
		System.out.println("Enter Processor's max issues per cycle :");
		int maxIssuesPerCycle = Integer.parseInt(sc.nextLine());
		Parser.copyProgramToMemory("program_1.in");
		System.out.println("Enter Number of AddUnits");
		int addUnits = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Number of MultUnits");
		int multUnits = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Number of LoadUnits");
		int loadUnits = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Number of StoreUnits");
		int storeUnits = Integer.parseInt(sc.nextLine());
		System.out.println("Enter Number of BranchUnits");
		int branchUnits = Integer.parseInt(sc.nextLine());

		Processor p = new Processor(maxIssuesPerCycle, addUnits, multUnits,
				loadUnits, storeUnits, branchUnits);
		while (true) {
			p.nextClockCycle();
			Instruction in = new Instruction(Caches.fetchInstruction(p.getPc()));
			p.setPc((short) (p.getPc() + 1));
			System.out.println(in.toString());
			if (p.isHalted())
				break;
			sc.nextLine();
		}
		sc.close();
	}
}
