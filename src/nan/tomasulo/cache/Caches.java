package nan.tomasulo.cache;

import java.util.LinkedList;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.memory.Memory;
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

	public CacheBlock readCacheBlock(short address, int currLevel,
			LinkedList<Cache> caches) throws InvalidReadException {
		if (currLevel == caches.size()) {
			// Base case (reached main memory)
			// when data is not found in any upper level
			Object[] data = Memory.readDataBlock(address);
			CacheBlock block = new CacheBlock(data);
			return block;
		}
		Cache currCache = caches.get(currLevel);
		CacheBlock block = currCache.getCacheBlock(address);
		if (!block.isValid() || block.getTag() != currCache.getTag(address)) {
			// Not found here try lower level

			// make sure the block is not dirty
			if (block.isValid()
					&& currCache.getWritePolicy() == WritePolicy.WRITE_BACK
					&& block.isDirty()) {
				// TODO Write the block to Main Memory
				block.setDirty(false);
			}
			// then update the current block with the result from lower level
			block.update(readCacheBlock(address, currLevel + 1, caches));
		}
		return block;
	}
}
