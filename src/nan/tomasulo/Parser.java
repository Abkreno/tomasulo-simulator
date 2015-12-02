package nan.tomasulo;

import nan.tomasulo.exceptions.InvalidWriteException;
import nan.tomasulo.memory.Memory;
import nan.tomasulo.utils.Utilities;

public class Parser {
	/**
	 * Copies the content of the assembly code to the program space in memory
	 * 
	 * @param filename
	 */
	public static void copyProgramToMemory(String filename) {
		String program = Utilities.readFile(filename);
		String[] lines = program.split("/");

		short startingLocation = 0;
		// starting point of the program
		if (lines[0].split(" ")[0].equalsIgnoreCase(".ORG")) {
			startingLocation = (short) Integer.parseInt(lines[0].split(" ")[1]);
		}
		Memory.init(16, lines.length, startingLocation);
		for (int i = 1; i < lines.length; i++) {
			try {
				Memory.writeDataEntry(startingLocation + i - 1, lines[i]);
			} catch (InvalidWriteException e) {
				e.printStackTrace();
			}
		}

	}

}
