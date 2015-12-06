package nan.tomasulo;

import java.util.Scanner;

import nan.tomasulo.cache.Caches;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.processor.Processor;

public class Simulator {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter Number of Caches:");
		int n = Integer.parseInt(sc.nextLine());
		int[][] cachesInfo = new int[n][4];
		String[] line;
		for (int i = 0; i < n; i++) {
			System.out.println("Enter the description of level " + (i + 1)
					+ " cache in the form S,L,m :");
			line = sc.nextLine().split(",");
			for (int j = 0; j < 3; j++) {
				cachesInfo[i][j] = Integer.parseInt(line[j]);
			}
			System.out
					.println("Enter Cache Write Policy 0 (for Write Through) / 1 (for Write Back): ");
			cachesInfo[i][3] = Integer.parseInt(sc.nextLine());
		}
		Caches.initCaches(cachesInfo);
		Parser.copyProgramToMemory("program_1.in");
		Processor p = new Processor();
		while (true) {
			p.nextClockCycle();
			if (p.getPc() == Memory.getProgramSize())
				break;
			sc.nextLine();
		}
		sc.close();
	}
}
