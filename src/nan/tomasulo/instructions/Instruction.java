package nan.tomasulo.instructions;

import nan.tomasulo.Parser;

public class Instruction {
	private String type;
	private int rd, rs, rt, imm;
	private boolean issued;

	public Instruction() {
		this.issued = true;
	}

	public Instruction(String instruction) {
		String[] split = instruction.split(" ");
		this.type = split[0];
		this.issued = false;
		int[] vals = new int[3];
		String[] split2 = split[1].split(",");
		for (int i = 0; i < split2.length; i++) {
			vals[i] = Parser.parseRegisters(split2[i]);
		}
		initializeRegistersValues(vals[0], vals[1], vals[2]);
	}

	public void initializeRegistersValues(int a, int b, int c) {
		if (Parser.checkTypeCondBranch(type) || Parser.checkTypeLoadStore(type)) {
			rd = a;
			rs = b;
			imm = c;
		} else if (Parser.checkTypeCall(type)) {
			rd = a;
			rs = b;
		} else if (Parser.checkTypeUncondBranch(type)) {
			rd = a;
			imm = b;
		} else if (Parser.checkTypeRet(type)) {
			rd = a;
		} else if (Parser.checTypeArithmetic(type)) {
			rd = a;
			rs = b;
			rt = c;
		} else if (Parser.checTypeImmArithmetic(type)) {
			rd = a;
			rs = b;
			imm = c;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getRs() {
		return rs;
	}

	public void setRs(int rs) {
		this.rs = rs;
	}

	public int getRt() {
		return rt;
	}

	public void setRt(int rt) {
		this.rt = rt;
	}

	public int getImmediate() {
		return imm;
	}

	public void setImmediate(int imm) {
		this.imm = imm;
	}

	public int getImm() {
		return imm;
	}

	public void setImm(int imm) {
		this.imm = imm;
	}

	public boolean isIssued() {
		return issued;
	}

	public void setIssued(boolean issued) {
		this.issued = issued;
	}

	public String toString() {
		return rd + " " + rs + " " + rt + " " + imm;
	}
}
