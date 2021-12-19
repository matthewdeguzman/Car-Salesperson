package carSalesperson;

import java.awt.Color;
import javax.swing.*;

public class TradeInScreen extends JFrame {
	private JLabel accNumber, tradePromptA, tradePromptB, costPrompt, 
					payPromptA, payPromptB, error, selectionText;
	private JTextField costInput;
	private JTextArea totalCost;
	private JButton enter, back, confirm, reset, select, next;
	private JComboBox paymentOptions;
	private double taxCost, tradeInPrice = 0.0;
	private final static String[] payOptions = {"--Select--", "Cash", "Finance Car (Loan)"};

	public TradeInScreen(String name, String address, String phoneNumber, String accountNumber,
			String car, double carPrice, double packagePrice, double metallicPaint, double tradeIn, boolean cash) {
		
		// Creating the account Number label
		accNumber = new JLabel("Account Number: " + accountNumber);
		accNumber.setBounds(30,10,150,20);
		
		// Creating the total cost label indicator
		costPrompt = new JLabel("Total Cost");
		costPrompt.setBounds(30,45,70,20);
		
		// Creating the total cost text area
		totalCost = new JTextArea(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, 0.0, false));
		totalCost.setEditable(false);
		totalCost.setBackground(this.getBackground());
		totalCost.setBounds(30,70,170,130);
		
		// Creating the trade-in label
		tradePromptA = new JLabel("Price of trade-in");
		tradePromptA.setBounds(229,45,100,20);
		
		// Creating another trade-in label instructing the user not to include commas
		tradePromptB = new JLabel("(Do not include commas)    $");
		tradePromptB.setBounds(229,60,250,20);
		
		// Creating the input field for the user to enter the trade-in price
		costInput = new JTextField();
		costInput.setBounds(390,59,80,23);
		
		// Creating the labels and asking the user how they would like to
		// pay for the rest of the cost if the car does not cover the 
		// entire cost
		payPromptA = new JLabel("How would you like to");
		payPromptA.setBounds(229,130,130,16);
		payPromptA.setVisible(false);
		payPromptB = new JLabel("cover the rest of the car?");
		payPromptB.setBounds(229,143,150,16);
		payPromptB.setVisible(false);
		
		// Creating the combo box for the remaining payment options
		paymentOptions = new JComboBox(payOptions);
		paymentOptions.setBounds(229,165,140,20);
		paymentOptions.setVisible(false);
		
		// Creating the label that tells the user what payment option they've selected
		selectionText = new JLabel();
		selectionText.setBounds(229,215,260,16);
		
		// Creating the select button
		select = new JButton("Select");
		select.setBounds(229,190,70,25);
		select.setBackground(Color.LIGHT_GRAY);
		select.setVisible(false);
		
		// Tells the user what payment option they've selected when the button is pressed
		select.addActionListener(e->{
			// Gets the payment option
			String selection = (String) paymentOptions.getItemAt(paymentOptions.getSelectedIndex());
			selectionText.setVisible(true);
			
			// If the user has selected a payment option, it will be displayed
			if (!selection.equals(payOptions[0])) {
				selectionText.setText("Payment Option Selected: " + selection);
				next.setVisible(true);
			}
			else {
				selectionText.setText("No Payment Option Selected");
				next.setVisible(false);
			}
		});
		
		// Creating the error label
		error = new JLabel("Error: Invalid Input");
		error.setBounds(390,110,120,20);
		error.setForeground(Color.RED);
		error.setVisible(false);
		
		// Creating the enter button
		enter = new JButton("Enter");
		enter.setBounds(390,85,75,25);
		enter.setBackground(Color.GREEN);
		
		// Retrieves the user's price of the trade-in and fires different
		// events depending on the price
		enter.addActionListener(e-> {
			try {
				tradeInPrice = Double.parseDouble(costInput.getText());
				
				// If the trade-in price is negative or 0, then an exception is thrown
				if (tradeInPrice <= 0.00)
					throw new NumberFormatException();
				
				// Hides certain components
				selectionText.setText("");
				next.setVisible(false);
				paymentOptions.setSelectedIndex(0);
				
				taxCost = TotalCost.getTax(carPrice, packagePrice, metallicPaint, tradeInPrice, 0.0, false);
				// If the tax cost is greater than zero, certain components are made visible
				// and some are hidden
				if (taxCost > 0.00) {
					confirm.setVisible(false);
					payPromptA.setVisible(true);
					payPromptB.setVisible(true);
					select.setVisible(true);
					selectionText.setVisible(false);
					paymentOptions.setVisible(true);
				}
				else {
					confirm.setVisible(true);
					payPromptA.setVisible(false);
					payPromptB.setVisible(false);
					select.setVisible(false);
					selectionText.setVisible(true);
					paymentOptions.setVisible(false);
				}
				
				reset.setVisible(true);
				totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, 0.0, false));
				error.setVisible(false);
			}
			catch(Exception er) {
				totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, 0.0, 0.0, false));
				error.setVisible(true);
				confirm.setVisible(false);
				payPromptA.setVisible(false);
				payPromptB.setVisible(false);
				select.setVisible(false);
				next.setVisible(false);
				selectionText.setVisible(false);
				paymentOptions.setVisible(false);
				reset.setVisible(false);
			}
		});
		
		// Creating the reset button
		reset = new JButton("Reset");
		reset.setBounds(305,85,75,25);
		reset.setBackground(Color.RED);
		reset.setForeground(Color.WHITE);
		reset.setVisible(false);
		
		// Hides components and resets the total cost text field
		reset.addActionListener(e-> {
			if (tradeInPrice != 0.00) {
				tradeInPrice = 0.0;
				totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeInPrice, 0.0, false));
				costInput.setText("");
				payPromptA.setVisible(false);
				payPromptB.setVisible(false);
				select.setVisible(false);
				selectionText.setText("");
				selectionText.setVisible(false);
				paymentOptions.setVisible(false);
				confirm.setVisible(false);
				reset.setVisible(false);
				next.setVisible(false);
			}
		});
		
		// Creates the back button
		back = new JButton("Back");
		back.setBounds(30,230,70,25);
		back.setBackground(Color.WHITE);
		
		// Brings the user to the previous screen and closes the current window
		back.addActionListener(e-> {
			new TotalCostScreen(name, address, phoneNumber, accountNumber, car, packagePrice, metallicPaint, "Trade-In");
			dispose();
		});
		
		// Creates the confirm button
		confirm = new JButton("Confirm Purchase");
		confirm.setBackground(Color.GREEN);
		confirm.setBounds(356,230,150,25);
		confirm.setVisible(false);
		
		// Brings the user to the thank you screen; the purchas is now over
		confirm.addActionListener(e-> {
			new ThankYouScreen();
			dispose();
		});
		
		// Creates the next button
		next = new JButton("Next");
		next.setBackground(Color.GREEN);
		next.setBounds(304,190,70,25);
		next.setVisible(false);
		
		// Brings the user to the next screen
		next.addActionListener(e-> {
			String selection = selectionText.getText().substring(selectionText.getText().indexOf(':') + 2, 
						selectionText.getText().length());
			// If the user selected cash, they are brought to the cash screen
			if (selection.equals(payOptions[1])) {
				new CashPayScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, tradeInPrice);
				dispose();
			}
			// If the selected a loan, then they are brought to the finance screen
			else {
				new FinanceCarScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, tradeInPrice);
				dispose();
			}
		});
		
		// If the trade-in parameter is greater than zero, it is set as the trade in price and the 
		// total cost is updated
		if (tradeIn > 0.0) {
			tradeInPrice = tradeIn;
			costInput.setText(String.valueOf(tradeIn));
			totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeIn, 0.0, false));
			reset.setVisible(true);
			payPromptA.setVisible(true);
			payPromptB.setVisible(true);
			select.setVisible(true);
			selectionText.setVisible(true);
			paymentOptions.setVisible(true);
			next.setVisible(true);
			
			// If cash is true, then the user has come from the cash screen
			// and the payment option is set to cash
			if (cash) {
				paymentOptions.setSelectedIndex(1);
				selectionText.setText("Payment Option Selected: " + payOptions[1]);
			}
			// If it is false, then the user came from the financing screen, 
			// and the payment option is set to finance
			else {
				paymentOptions.setSelectedIndex(2);
				selectionText.setText("Payment Option Selected: " + payOptions[2]);
			}
		}
		
		// Adding the components
		add(accNumber);add(costPrompt);add(tradePromptA);add(tradePromptB);
		add(payPromptA);add(payPromptB);add(paymentOptions);add(select);
		add(totalCost);add(error);add(reset);add(selectionText);add(enter);
		add(back);add(next);add(costInput);add(confirm);
		
		setSize(550,300);
		setTitle("Car Selector");
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}