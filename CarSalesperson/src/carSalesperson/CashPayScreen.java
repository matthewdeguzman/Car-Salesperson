package carSalesperson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CashPayScreen extends JFrame {
	private JLabel accNumber, costPrompt;
	private JTextArea totalCost;
	private JButton back, confirm;
	
	public CashPayScreen(String name, String address, String phoneNumber, String accountNumber,
			String car, double carPrice, double packagePrice, double metallicPaint, double tradeIn) {
		
		// Creates the account number label
		accNumber = new JLabel("Account Number: " + accountNumber);
		accNumber.setBounds(30,10,150,20);
		
		// Creates the total cost label
		costPrompt = new JLabel("Total Cost Calculation");
		costPrompt.setBounds(200,35,243,16);
		
		// Creates the total cost label to display the total cost of the car and what contributes to it
		totalCost = new JTextArea();
		totalCost.setBounds(180,55,190,150);
		totalCost.setBackground(this.getBackground());
		totalCost.setEditable(false);
		if (tradeIn > 0.00)	// If the user has a trade in, then it is considered in the total price receipt
			totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeIn, 0.0, false));
		else
			totalCost.setText(TotalCost.getTotalCost(car, carPrice, packagePrice, metallicPaint, tradeIn, 0.0, true));

		// Creates the back button
		back = new JButton("Back");
		back.setBounds(30,230,70,25);
		back.setBackground(Color.WHITE);
		
		// Sends the user to their previous screen
		back.addActionListener(e-> {
			// If the trade-in price is greater than 0, then the user has come from the 
			// trade in screen, and is sent back to the trade in screen
			if (tradeIn > 0.00)
				new TradeInScreen(name, address, phoneNumber, accountNumber, car, carPrice, packagePrice, metallicPaint, tradeIn, true);
			// If trade in is 0, then the user has come from the total cost screen and
			// they are sent back to the total cost screen
			else 
				new TotalCostScreen(name, address, phoneNumber, accountNumber, car, packagePrice, metallicPaint, "Cash");
			dispose();
		});
		
		// Creates the confirm purchase button
		confirm = new JButton("Confirm Purchase");
		confirm.setBackground(Color.GREEN);
		confirm.setBounds(360,230,150,25);
		
		// Sends the user to the thank you screen, and the purchase is now over
		confirm.addActionListener(e-> {
			new ThankYouScreen();
			dispose();
		});
		
		add(accNumber);add(costPrompt);add(totalCost);add(back);add(confirm);
		
		setSize(550,300);
		setTitle("Car Selector");
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}