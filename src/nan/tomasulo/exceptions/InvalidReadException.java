package nan.tomasulo.exceptions;

@SuppressWarnings("serial")
public class InvalidReadException extends Exception {
	public InvalidReadException() {
		super("Invalid read from memory, check the address space");
	}

	public InvalidReadException(String message) {
		super(message);
	}
}
