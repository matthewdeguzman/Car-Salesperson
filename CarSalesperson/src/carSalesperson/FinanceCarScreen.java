package carSalesperson;

import javax.swing.*;
import java.awt.*;

public class FinanceCarScreen extends JFrame {
	private JTextArea totalCost;
	private JLabel accNumber, loanPrompt, downPaymentPromptA, downPaymentPromptB, 
					error, minimumPay , monthPayA, monthPayB, numPayments;
	private JTextField downPayment, payMonths;
	private JButton back, next, enter, reset;
	private double totalPrice, monthlyPayPrice, minPay, dpPrice = 0.00;
	private int months;
	private static final double MONTHLY_INTEREST = 0.07/12.0;
	
	public FinanceCarScreen(String name, String address, String phoneNumber, String accountNumber,
			String car, double carPrice, double packagePrice, double metallicPaint, double tradeInPrice) {
		// Creating the account number label
		accNumber = new JLabel("Account Number: " + accountNumber);
		accNumber.setBounds(30,10,150,20);
		
		// Creates the label that tells the user what percentage the interest on the loan is
		loanPrompt = new JLabel("Finance Loan at 7% Annual Interest");
		loanPrompt.setForeground(Color.BLUE);
		loanPrompt.setBounds(229,10,300,16);
		
		// Creates the text area that tells the user how much the cost of the car is and what contributes 
		totalCost = new JTextArea(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, dpPrice, false));
		totalCost.setEditable(false);
		totalCost.setBackground(this.getBackground());
		totalCost.setBounds(30,70,170,150);
		
		// Creates labels to tell the user to enter the down payment amount
		downPaymentPromptA= new JLabel("Initial Down Payment           $");	
		downPaymentPromptA.setBounds(229,28,200,20);
		downPaymentPromptB = new JLabel("(Do not include commas)");
		downPaymentPromptB.setBounds(229,43,180,20);
		
		// Creates the down payment input text field
		downPayment = new JTextField();
		downPayment.setBounds(390,28,80,23);
		
		// Creates labels prompting the user to enter the monthly payment for the loan
		monthPayA = new JLabel("Monthly Payment Amount   $");
		monthPayA.setBounds(229,67,300,20);
		monthPayB = new JLabel("(Do not include commas)");
		monthPayB.setBounds(229,82,180,20);
		
		// Creates the input text field for the user to enter the monthly payment
		payMonths = new JTextField();
		payMonths.setBounds(390,67,80,23);
		
		// Creates the enter button for the user
		enter = new JButton("Enter");
		enter.setBounds(390,103,75,25);
		enter.setBackground(Color.GREEN);
		
		// Gathers input from the down payment and monthly payment and tells the user
		// what the minimum monthly payment is, how many payments it will take to complete
		// the loan, and updates the total cost
		enter.addActionListener(e-> {
			try {
				minimumPay.setText("");		// Hides the minimum text because it may be updated
				error.setVisible(false);	// Hides the error, because an error has not occurred yet
				
				// Checks if the user has entered any down payment, it is set to 0.00
				if (downPayment.getText().isEmpty() || downPayment.getText().isBlank()) {
					// If the user hasn't entered anything, the down payment is set to zero
					downPayment.setText("0.00");
					dpPrice = 0.00;
				}
				else	// If the user has entered information, it is parsed to a double															
					dpPrice = Double.parseDouble(downPayment.getText());
				
				// If the down payment is negative an error is thrown
				// If the down payment is greater than or equal to the total cost before the down payment is applied, the payment is 
				// too large and an error is thrown
				if (dpPrice < 0.00 || dpPrice >= TotalCost.getPrice(carPrice, packagePrice, metallicPaint, tradeInPrice, 0.0, false)) 
					throw new InvalidPaymentAmount(dpPrice);
				
				// Calculates the tax cost, total price, and the minimum monthly payment
				totalPrice = TotalCost.getPrice(carPrice, packagePrice, metallicPaint, tradeInPrice, dpPrice, false);
				minPay = (Math.round((MONTHLY_INTEREST * totalPrice) * 100.0)/100.0) + 0.01;
				
				// Informs the user what the minimum monthly payment is at the current total price
				minimumPay.setText("Minimum Monthly Payment: $" + String.format("%,.2f", minPay));
				minimumPay.setVisible(true);
				
				// Updates and displays the total cost of the car
				totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, dpPrice, false));
				
				// If the user hasn't entered the monthly payment, their monthly payment is set as the minimum payment
				if (payMonths.getText().isEmpty() || payMonths.getText().isBlank()) {
					monthlyPayPrice = minPay;
					payMonths.setText(String.format("%,.2f", monthlyPayPrice));
				}
				else																	
					monthlyPayPrice = Double.parseDouble(payMonths.getText());
				
				// If the monthly payment amount is less than the minimum amount or greater than the total cost of the car, an error is thrown
				if (monthlyPayPrice < minPay || monthlyPayPrice > totalPrice)	
					throw new InvalidPaymentAmount(monthlyPayPrice);
				
				// Calculates and informs the user how many months (payments) it may take to finish the loan					
				months = (int) Math.round((Math.log(1.0 / (1.0 - (totalPrice * MONTHLY_INTEREST) / monthlyPayPrice))) / Math.log(1.0 + MONTHLY_INTEREST));
				numPayments.setText("Payments to Finish Loan: " + months);
				numPayments.setVisible(true);
				
				// Sets the reset and next buttons to visible
				reset.setVisible(true);
				next.setVisible(true);
			}
			// If there is an InvalidPaymentAmount error, then the error is set as the message,
			// the next and number of payments to complete the loan are hidden, and the error
			// is shown
			catch(InvalidPaymentAmount er) {
				error.setText(er.getMessage());
				next.setVisible(false);
				numPayments.setVisible(false);
				error.setVisible(true);
			}
			// If there is any other exception (With parsing doubles or other input errors),
			// An error message is displayed and the next button is hidden
			catch(Exception er) {
				error.setText("Error: Invalid Input");
				next.setVisible(false);
				numPayments.setVisible(false);
				error.setVisible(true);
			}
		});
		
		// Creates the error label
		error = new JLabel();
		error.setBounds(229,128,250,20);
		error.setForeground(Color.RED);
		error.setVisible(false);
		
		// Creates the minimum monthly payment label
		minimumPay = new JLabel();
		minimumPay.setBounds(229,145,250,20);
		minimumPay.setVisible(false);
		
		// Creates the label to tell the user the number of payments to complete the loan
		numPayments = new JLabel();
		numPayments.setBounds(229,160,250,20);
		numPayments.setVisible(false);
		
		// Creates the reset button
		reset = new JButton("Reset");
		reset.setBounds(305,103,75,25);
		reset.setBackground(Color.RED);
		reset.setForeground(Color.WHITE);
		reset.addActionListener(e-> {
			dpPrice = 0.00;
			totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, dpPrice, false));
			downPayment.setText("");
			reset.setVisible(false);
			minimumPay.setVisible(false);
			payMonths.setText("");
			numPayments.setVisible(false);
			next.setVisible(false);
		});
		reset.setVisible(false);
		
		// Creates the back button
		back = new JButton("Back");
		back.setBounds(30,230, 70,25);
		back.setBackground(Color.WHITE);
		
		// Allows the user to go to the previous screen and closes the current window
		back.addActionListener(e-> {
			if (tradeInPrice > 0.00)
				new TradeInScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, tradeInPrice, false);
			else
				new TotalCostScreen(name, address, phoneNumber, accountNumber, car, packagePrice, metallicPaint, "Finance Car (Loan)");
			dispose();
		});
		
		// Creates the next button
		next = new JButton("Confirm Purchase");
		next.setBackground(Color.GREEN);
		next.setBounds(356,230,150,25);
		next.setVisible(false);
		
		// Takes the user to the exit screen because the purchase is now over
		next.addActionListener(e-> {
			new ThankYouScreen();
			dispose();
		});
		
		add(accNumber);add(loanPrompt);add(totalCost);
		add(downPaymentPromptA);add(downPaymentPromptB);
		add(downPayment);add(monthPayA);add(monthPayB);
		add(payMonths);add(enter);add(error);add(minimumPay);
		add(numPayments);add(reset);add(back);add(next);
		
		setSize(550,300);
		setTitle("Car Selector");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}