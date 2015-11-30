package nan.tomasulo.processor;

public class Process {
	String operation;
	int issue;
	int execute;
	int write;
	int latency;
	boolean finished = false;

	public Process(String operation, int issue, int execute, int write,
			int latency, boolean finished) {
		this.operation = operation;
		this.issue = issue;
		this.execute = execute;
		this.write = write;
		this.latency = latency;
		this.finished = finished;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public int getExecute() {
		return execute;
	}

	public void setExecute(int execute) {
		this.execute = execute;
	}

	public int getWrite() {
		return write;
	}

	public void setWrite(int write) {
		this.write = write;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
}
