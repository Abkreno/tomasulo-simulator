package nan.tomasulo.cache;

import java.util.LinkedList;

public class Caches {
	private static Cache dataCacheL1, instructionCacheL1;
	private static LinkedList<Cache> caches; // Lower Level Caches

	public static void initCaches(int cacheInfo[][]) {
		caches = new LinkedList<>();
		dataCacheL1 = new Cache(cacheInfo[0]);
		instructionCacheL1 = new Cache(cacheInfo[0]);
		for (int i = 1; i < cacheInfo.length; i++) {
			caches.add(new Cache(cacheInfo[i]));
		}
	}

	public static Cache getDataCacheL1() {
		return dataCacheL1;
	}

	public static Cache getInstructionCacheL1() {
		return instructionCacheL1;
	}

	public static LinkedList<Cache> getCaches() {
		return caches;
	}
}
