package nan.tomasulo.cache;

import java.util.LinkedList;

import nan.tomasulo.exceptions.InvalidReadException;
import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.utils.Constants.WritePolicy;

public class Caches {
	private static LinkedList<Cache> dataCaches, instructionCaches;

	public static void initCaches(int cacheInfo[][]) {
		dataCaches = new LinkedList<>();
		instructionCaches = new LinkedList<>();
		for (int i = 0; i < cacheInfo.length; i++) {
			dataCaches.add(new Cache(cacheInfo[i][0], cacheInfo[i][1],
					cacheInfo[i][2],
					cacheInfo[i][3] == 0 ? WritePolicy.WRITE_THROUGH
							: WritePolicy.WRITE_BACK));
		}
		for (int i = 0; i < cacheInfo.length; i++) {
			instructionCaches.add(new Cache(cacheInfo[i][0], cacheInfo[i][1],
					cacheInfo[i][2],
					cacheInfo[i][3] == 0 ? WritePolicy.WRITE_THROUGH
							: WritePolicy.WRITE_BACK));
		}
	}

	public static LinkedList<Cache> getDataCaches() {
		return dataCaches;
	}

	public static LinkedList<Cache> getInstructionCaches() {
		return instructionCaches;
	}

	public static CacheBlock readCacheBlock(short address, int currLevel,
			LinkedList<Cache> caches) throws InvalidReadException,
			InvalidWriteException {
		if (currLevel == caches.size()) {
			// Base case (reached main memory)
			// when data is not found in any upper level
			Object[] memData = Memory.readDataBlock(address);
			CacheBlock block = new CacheBlock(memData, caches.get(0)
					.getBlockSize());
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
				Memory.writeBlock(block.getAddress(), block.getEntries());
				block.setDirty(false);
			}
			currCache.setMisses(currCache.getMisses() + 1);
			// then update the current block with the result from lower level
			block.update(readCacheBlock(address, currLevel + 1, caches));
			block.setTag(currCache.getTag(address));
			block.setAddress((short) ((address / block.getSize()) * block
					.getSize()));
		}
		currCache.setHits(currCache.getHits() + 1);
		return block;
	}

	public static void writeCacheBlock(short address, int currLevel,
			Object data, LinkedList<Cache> caches) throws InvalidReadException,
			InvalidWriteException {
		if (currLevel == caches.size()) {
			// Base case (reached main memory)
			Memory.writeDataEntry(address, data);
			return;
		}
		Cache currCache = caches.get(currLevel);
		CacheBlock block;
		if (currLevel == 0) {
			// Read the block using readCacheBlock to handle if the block is not
			// in current level
			block = readCacheBlock(address, currLevel, caches);
		} else {
			// the block has been read in level 0
			block = currCache.getCacheBlock(address);
		}
		if (currCache.getWritePolicy() == WritePolicy.WRITE_BACK) {
			// Write here and set dirty
			CacheEntry entry = block.getEntries()[currCache.getOffset(address)];
			entry.setData(data);
			block.setDirty(true);
		} else if (currCache.getWritePolicy() == WritePolicy.WRITE_THROUGH) {
			// Write here and write in the next level
			CacheEntry entry = block.getEntries()[currCache.getOffset(address)];
			entry.setData(data);
			writeCacheBlock(address, currLevel + 1, data, caches);
		}
	}

	public static Short fetchData(short address) throws InvalidReadException,
			InvalidWriteException {
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getDataCaches());
		short offset = (short) (address % block.getSize());
		Short data = (Short) block.getEntries()[offset].getData();
		return data;
	}

	public static String fetchInstruction(short instructionNumber)
			throws InvalidReadException, InvalidWriteException {
		short address = (short) (instructionNumber + Memory
				.getProgramBeginning());
		CacheBlock block = Caches.readCacheBlock(address, 0,
				Caches.getInstructionCaches());
		short offset = (short) (address % block.getSize());
		String instruction = (String) block.getEntries()[offset].getData();
		return instruction;
	}

}
