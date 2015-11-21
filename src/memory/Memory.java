package memory;

public class Memory {
	private short[] memory_data;
	private int memory_size;
	private int block_size; // Block Size = # of words inside the block

	public Memory() {
		this.memory_size = 64 * 1024 / 16;
		this.memory_data = new short[memory_size];
	}

	public Memory(int size) {
		this.memory_size = size;
		this.memory_data = new short[memory_size];
	}

	public void setData(int address, short data) {
		memory_data[address] = data;
	}

	public short getData(int address) {
		return memory_data[address];
	}
}
