package carSalesperson;

public class InvalidInput extends Exception {
	public InvalidInput() {
		super();
	}
	
	public InvalidInput(String n) {
			super("Error: " + n + " is Empty");
	}
	public InvalidInput(int i, String n) {
		super("Error: " + n + " Filled Out Incorrectly");
	}
}
