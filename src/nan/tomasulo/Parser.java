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
			Memory.init(16, lines.length - 1, startingLocation);
		} else {
			Memory.init(16, lines.length, startingLocation);
		}
		for (int i = 1; i < lines.length; i++) {
			try {
				Memory.writeDataEntry(startingLocation + i - 1, lines[i]);
			} catch (InvalidWriteException e) {
				e.printStackTrace();
			}
		}

	}

	public static boolean checkTypeImmArithmetic(String type) {
		return type.equalsIgnoreCase("ADDI");
	}

	public static boolean checkTypeMult(String type) {
		return type.equalsIgnoreCase("MUL");
	}

	public static boolean checkTypeLogical(String type) {
		return type.equalsIgnoreCase("NAND");
	}

	public static boolean checkTypeArithmetic(String type) {
		return type.equalsIgnoreCase("ADD") || type.equalsIgnoreCase("SUB")
				|| type.equalsIgnoreCase("NAND")
				|| type.equalsIgnoreCase("MUL");
	}

	public static boolean checkTypeRet(String type) {
		return type.equalsIgnoreCase("RET");
	}

	public static boolean checkTypeUncondBranch(String type) {
		return type.equalsIgnoreCase("JMP");
	}

	public static boolean checkTypeCall(String type) {
		return type.equalsIgnoreCase("JALR");
	}

	public static boolean checkTypeLoadStore(String type) {
		return type.equalsIgnoreCase("LW") || type.equalsIgnoreCase("SW");
	}

	public static boolean checkTypeCondBranch(String type) {
		return type.equalsIgnoreCase("BREQ");
	}

	public static int parseRegisters(String in) {
		if (in.contains("0x")) {
			return Integer.parseInt(in.substring(2), 16);
		}
		if (in.contains("0b")) {
			return Integer.parseInt(in.substring(2), 2);
		}
		if (in.contains("R") || in.contains("r"))
			return Integer.parseInt(in.substring(1));
		return Integer.parseInt(in);
	}

}
