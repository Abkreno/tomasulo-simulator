package nan.tomasulo.utils;

public class Constants {
	public static int BLOCK_SIZE;
	public static int CACHE_LEVELS;

	public enum CacheType {
		INSTRUCTION_CAHCE, DATA_CACHE
	}

	public enum WritePolicy {
		WRITE_THROUGH, WRITE_BACK
	}

}
