package carSalesperson;

public class InvalidPaymentAmount extends Exception {
	public InvalidPaymentAmount() {
		super("Error: Invalid Amount");
	}
	public InvalidPaymentAmount(double amount) {
		super("Error: Invalid Payment Amount: $" + String.format("%,.2f", amount));
	}
}
