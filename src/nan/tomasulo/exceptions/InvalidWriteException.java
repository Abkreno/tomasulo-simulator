package nan.tomasulo.exceptions;

public class InvalidWriteException extends Exception {
	public InvalidWriteException() {
		super("Invalid write to memory, check the address space");
	}

	public InvalidWriteException(String message) {
		super(message);
	}
}
