package carSalesperson;

import javax.swing.*;
import java.awt.*;

public class TotalCostScreen extends JFrame {
	private JTextArea totalCost;
	private JLabel accNumber, price, paymentPrompt, selectText;
	private JButton back, next, select;
	private JComboBox paymentOptions;
	private static final String[] payOptions = {"--Select--","Cash","Trade-In","Finance Car (Loan)"};
	
	public TotalCostScreen(String name, String address, String phoneNumber, String accountNumber,
							String car, double packagePrice, double metallicPaint, String paymentMethod) {
		
		// Creating the account number label
		accNumber = new JLabel("Account Number: " + accountNumber);
		accNumber.setBounds(30,10,150,20);
		
		// Creating the total cost label indicator
		price = new JLabel("Total Cost Calculation:");
		price.setBounds(30,45,160,20);
		
		// Creating the total cost text area
		totalCost = new JTextArea();
		totalCost.setBackground(this.getBackground());
		totalCost.setText(TotalCost.getTotalCost(car, 0.0, packagePrice, metallicPaint, 0.0, 0.0, false));
		totalCost.setBounds(30,70,200,220);
		totalCost.setEditable(false);
		
		// Ask the user for their method of payment
		paymentPrompt = new JLabel("Please select a method of payment");
		paymentPrompt.setBounds(301,45,199,20);
		
		// Create combo box with the payment options
		paymentOptions = new JComboBox(payOptions);
		paymentOptions.setBounds(301,70,140,20);
		
		// Create selection text prompt
		selectText = new JLabel();
		selectText.setBounds(301,130,250,20);
		
		// Create back button
		back = new JButton("Back");
		back.setBounds(345,223,70,25);
		back.setBackground(Color.WHITE);
		
		// Brings the user back to the previous screen and closes the current window
		back.addActionListener(e-> {			
			// Initiates the PackageScreen constructor 
			new PackageScreen(name, phoneNumber, address, accountNumber, car, packagePrice, metallicPaint);
			dispose();	// Close window
		});
		
		// Create select button
		select = new JButton("Select");
		select.setBounds(301,100,80,25);
		select.setBackground(Color.LIGHT_GRAY);
		
		// Tells the user what payment option they selected and allows them to 
		select.addActionListener(e-> {
			// go to the next screen
			String selection = (String) paymentOptions.getItemAt(paymentOptions.getSelectedIndex());
			// If the user did not choose the --select-- option, their method is displayed
			if (!selection.equals(payOptions[0])) {		
				selectText.setText("Payment Method: " + selection);
				next.setVisible(true);
			}
			// If the user chose the --select-- option, they haven't selected a payment method
			else {
				selectText.setText("No Payment Selected");
				next.setVisible(false);
			}
		});
		
		// Create next button
		next = new JButton("Next");
		next.setBackground(Color.GREEN);
		next.setBounds(430,223,70,25);
		next.setVisible(false);
		
		// Determines what payment method the user selected and brings them to the next screen
		next.addActionListener(e-> {
			 // and closes the current window
			double carPrice = TotalCost.getCarPrice(car);
			String selection = selectText.getText().substring(selectText.getText().indexOf(':') + 2);
			if (selection.equals(payOptions[1]))
				new CashPayScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, 0.0);
			else if (selection.equals(payOptions[2]))
				new TradeInScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, 0.0, false);
			else
				new FinanceCarScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, 0.0);
			dispose();
		});
		
		// If there was a previously selected payment method, it is set as the current payment method
		if (paymentMethod != null) {
			selectText.setText("Payment Method: " + paymentMethod);
			selectText.setVisible(true);
			next.setVisible(true);
			for (int i = 1; i < payOptions.length; i++)
				if (payOptions[i].equals(paymentMethod))
					paymentOptions.setSelectedIndex(i);
		}
		
		// Adds the components to the window
		add(accNumber);add(price);add(totalCost);
		add(paymentPrompt);add(paymentOptions);add(selectText);
		add(back);add(select);add(next);
		
		setSize(550,300);
		setTitle("Car Selector");
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}