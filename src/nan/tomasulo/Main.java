package nan.tomasulo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader bf = new BufferedReader(InputStreamReader(System.in));
		System.out.println("Enter Number of Caches:");
		int n = Integer.parseInt(bf.readLine());
		String[][] cacheDescription = new String[n][];
		for (int i = 0; i < n; i++) {
			System.out.println("Enter the description of level " + (i + 1)
					+ " cache in the form S,L,m :");
			cacheDescription[i] = bf.readLine().split(",");
		}
	}
}
