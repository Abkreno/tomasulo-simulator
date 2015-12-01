package nan.tomasulo.cache;

import java.util.LinkedList;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.utils.Constants;
import nan.tomasulo.utils.Constants.WritePolicy;

public class Caches {
	private static LinkedList<Cache> dataCaches, instructionCaches;

	public static void initCaches(int cacheInfo[][], int policy) {
		dataCaches = new LinkedList<>();
		instructionCaches = new LinkedList<>();
		for (int i = 0; i < cacheInfo.length; i++) {
			dataCaches.add(new Cache(cacheInfo[i],
					policy == 0 ? WritePolicy.WRITE_THROUGH
							: WritePolicy.WRITE_BACK));
		}
	}

	public static LinkedList<Cache> getDataCaches() {
		return dataCaches;
	}

	public static LinkedList<Cache> getInstructionCaches() {
		return instructionCaches;
	}

	public CacheBlock readDataBlock(short address, int currLevel)
			throws InvalidReadException {
		if (currLevel == Constants.CACHE_LEVELS) {
			// Base case ,reached when data is not found in any upper level
			Object[] data = Memory.readDataBlock(address);
			CacheBlock block = new CacheBlock(data);
			return block;
		}
		Cache currCache = Caches.getDataCaches().get(currLevel);
		CacheBlock block = currCache.getCacheBlock(address);
		if (block == null) {
			// Not found here try lower level
			// update the current block with the result from lower level
			block.update(readDataBlock(address, currLevel + 1));
		}
		return block;
	}
}
