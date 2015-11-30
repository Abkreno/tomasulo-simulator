package nan.tomasulo;

import java.util.Scanner;

import nan.tomasulo.cache.Caches;

public class Simulator {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Number of Caches:");
		int n = Integer.parseInt(sc.nextLine());
		int[][] cachesInfo = new int[n][3];
		String[] line;
		for (int i = 0; i < n; i++) {
			System.out.println("Enter the description of level " + (i + 1)
					+ " cache in the form S,L,m :");
			line = sc.nextLine().split(",");
			for (int j = 0; j < 3; j++) {
				cachesInfo[i][j] = Integer.parseInt(line[j]);
			}
		}
		Caches.initCaches(cachesInfo);
		sc.close();
	}
}
